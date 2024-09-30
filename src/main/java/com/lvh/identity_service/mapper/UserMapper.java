package com.lvh.identity_service.mapper;

import com.lvh.identity_service.dto.request.UserCreationRequest;
import com.lvh.identity_service.dto.request.UserUpdateRequest;
import com.lvh.identity_service.dto.response.UserResponse;
import com.lvh.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
}
