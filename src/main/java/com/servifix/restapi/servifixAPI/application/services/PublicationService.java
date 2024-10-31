package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.PublicationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PublicationResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface PublicationService {

    ApiResponse<List<PublicationResponseDTO>> getPublications();

    ApiResponse<List<PublicationResponseDTO>> getPublicationByUserId(int userId);

    ApiResponse<PublicationResponseDTO> getPublicationById(int id);

    ApiResponse<PublicationResponseDTO> createPublication(PublicationRequestDTO publicationRequestDTO);

    ApiResponse<PublicationResponseDTO> updatePublication(int id, PublicationRequestDTO publicationRequestDTO);

    ApiResponse<Void> deletePublication(int id);

}
