package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.RoleRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.RoleResponseDto;
import com.servifix.restapi.servifixAPI.domain.entities.Role;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface RoleService {

    Role getRoleById(int id);

    ApiResponse<RoleResponseDto> createRole(RoleRequestDTO roleRequestDTO);

    ApiResponse<RoleResponseDto> updateRole(int id, RoleRequestDTO roleRequestDTO);

    ApiResponse<Void> deleteRole(int id);

    boolean existRole(int id);
}
