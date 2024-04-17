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
}
