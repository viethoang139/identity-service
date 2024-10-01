package com.lvh.identity_service.service;

import com.lvh.identity_service.dto.request.UserCreationRequest;
import com.lvh.identity_service.dto.request.UserUpdateRequest;
import com.lvh.identity_service.dto.response.UserResponse;
import com.lvh.identity_service.entity.User;
import com.lvh.identity_service.enums.Role;
import com.lvh.identity_service.exception.AppException;
import com.lvh.identity_service.exception.ErrorCode;
import com.lvh.identity_service.mapper.UserMapper;
import com.lvh.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
     UserRepository userRepository;
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
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> userMapper.toUserResponse(user)).collect(Collectors.toList());
    }

    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER)));
    }
    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_USER));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

}
