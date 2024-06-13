package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.UserRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserResponseDTO>> getUsers();

    ApiResponse<UserResponseDTO> getUserById(int id);

    ApiResponse<Void> deleteUser(int id);

    ApiResponse<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);

    ApiResponse<UserResponseDTO> updateUser(int id, UserRequestDTO userRequestDTO);

    ApiResponse<UserResponseDTO> getUserByAccount(int account_id);
}
