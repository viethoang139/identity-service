package com.lvh.identity_service.service;

import com.lvh.identity_service.dto.request.RoleRequest;
import com.lvh.identity_service.dto.response.RoleResponse;
import com.lvh.identity_service.entity.Permission;
import com.lvh.identity_service.entity.Role;
import com.lvh.identity_service.mapper.RoleMapper;
import com.lvh.identity_service.repository.PermissionRepository;
import com.lvh.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    public RoleResponse create(RoleRequest request){
        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }

}
