package com.lvh.identity_service.service;

import com.lvh.identity_service.dto.request.UserCreationRequest;
import com.lvh.identity_service.dto.request.UserUpdateRequest;
import com.lvh.identity_service.dto.response.UserResponse;
import com.lvh.identity_service.entity.User;
import com.lvh.identity_service.enums.Role;
import com.lvh.identity_service.exception.AppException;
import com.lvh.identity_service.exception.ErrorCode;
import com.lvh.identity_service.mapper.UserMapper;
import com.lvh.identity_service.repository.RoleRepository;
import com.lvh.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
     UserRepository userRepository;
     RoleRepository roleRepository;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request){


        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
       // user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String userId) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER)));
    }
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));

        return userMapper.toUserResponse(user);

    }
    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

}
