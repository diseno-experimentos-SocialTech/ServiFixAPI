package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.RoleRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.RoleResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.RoleService;
import com.servifix.restapi.servifixAPI.domain.entities.Role;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.RoleRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Role getRoleById(int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        return roleOptional.orElse(null);
    }

    @Override
    public ApiResponse<RoleResponseDTO> createRole(RoleRequestDTO roleRequestDTO) {

        var role = modelMapper.map(roleRequestDTO, Role.class);
        validateRole(roleRequestDTO);
        roleRepository.save(role);

        var response = modelMapper.map(role, RoleResponseDTO.class);

        return new ApiResponse<>("Role created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<RoleResponseDTO> updateRole(int id, RoleRequestDTO roleRequestDTO) {

        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            return new ApiResponse<>("Role not found", Estatus.ERROR, null);
        }else {
            Role role = roleOptional.get();
            modelMapper.map(roleRequestDTO, role);
            validateUpdateRole(id, roleRequestDTO);
            roleRepository.save(role);
            RoleResponseDTO response = modelMapper.map(role, RoleResponseDTO.class);
            return new ApiResponse<>("Role updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteRole(int id) {

        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            return new ApiResponse<>("Role not found", Estatus.ERROR, null);
        }else {
            roleRepository.deleteById(id);
            return new ApiResponse<>("Role deleted successfully", Estatus.SUCCESS, null);
        }
    }

    @Override
    public boolean existRole(int id) {
        return roleRepository.existsById(id);
    }

    private void validateRole(RoleRequestDTO role) {
        if (isRoleExists(role.getType())) {
            throw new RuntimeException("A Role with the same name already exists");
        }
        if (!isValidateRoleType(role.getType())) {
            throw new RuntimeException("The Role name can only be Admin, Tecnicos or Usuario");
        }
    }

    private void validateUpdateRole(int id, RoleRequestDTO role) {
        if (isRoleExistsById(role.getType(), id)){
            throw new RuntimeException("Role not found");
        }
        if (!isValidateRoleType(role.getType())) {
            throw new RuntimeException("The Role name can only be Admin, Tecnicos or Usuario");
        }
    }

    private boolean isRoleExists(String type) {
        return roleRepository.existsByType(type);
    }

    private boolean isRoleExistsById(String type, int id) {
        return roleRepository.existsByTypeAndIdNot(type, id);
    }
    private boolean isValidateRoleType(String type) {
        return type.equals("Admin") || type.equals("Tecnicos") || type.equals("Usuario");
    }


}
