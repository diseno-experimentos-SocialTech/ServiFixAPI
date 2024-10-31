package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.RoleRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.RoleResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.RoleService;
import com.servifix.restapi.servifixAPI.domain.entities.Role;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Rol", description = "Rol API")
@RestController
@RequestMapping("/api/v1/servifix")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "get a role by id")
    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable int id) {
        var res = roleService.getRoleById(id);
        return res;
    }

    @Operation(summary = "create a new role")
    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        var res = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "update an existing role")
    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> updateRole(@PathVariable int id, @RequestBody RoleRequestDTO roleRequestDTO) {
        var res = roleService.updateRole(id, roleRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "delete a role")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable int id) {
        var res = roleService.deleteRole(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
