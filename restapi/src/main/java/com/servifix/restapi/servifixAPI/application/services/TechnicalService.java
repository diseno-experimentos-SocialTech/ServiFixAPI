package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.TechnicalRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface TechnicalService {

    ApiResponse<List<TechnicalResponseDTO>> getTechnicals();

    ApiResponse<TechnicalResponseDTO> getTechnicalById(int id);

    ApiResponse<Void> deleteTechnical(int id);

    ApiResponse<TechnicalResponseDTO> createTechnical(TechnicalRequestDTO TechnicalRequestDTO);

    ApiResponse<TechnicalResponseDTO> updateTechnical(int id, TechnicalRequestDTO TechnicalRequestDTO);

    ApiResponse<TechnicalResponseDTO> getTechnicalByAccount(int account_id);

    ApiResponse<TechnicalResponseDTO> getTechnicalByAccount_FirstName(String firstName);

    ApiResponse<TechnicalResponseDTO> getTechnicalByAccount_LastName(String lastName);

}
