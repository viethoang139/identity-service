package com.lvh.identity_service.controller;

import com.lvh.identity_service.dto.request.ApiResponse;
import com.lvh.identity_service.dto.request.UserCreationRequest;
import com.lvh.identity_service.dto.request.UserUpdateRequest;
import com.lvh.identity_service.dto.response.UserResponse;
import com.lvh.identity_service.entity.User;
import com.lvh.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createRequest(request));
        return apiResponse;
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId,
                            @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "Delete successfully";
    }
}
