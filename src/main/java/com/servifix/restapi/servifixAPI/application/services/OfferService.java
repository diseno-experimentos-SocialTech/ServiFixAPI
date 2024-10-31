package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.OfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.OfferResponseDTO;
import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface OfferService {

    ApiResponse<OfferResponseDTO> getOfferById(int id);

    ApiResponse<List<OfferResponseDTO>> getOffersByTechnicalId(int technicalId);

    ApiResponse<List<OfferResponseDTO>> getOffersByPublicationId(int publicationId);

    ApiResponse<List<OfferResponseDTO>> getOffersByPublicationIdAndStateOfferId(int publicationId, int stateId);

    ApiResponse<OfferResponseDTO> createOffer(OfferRequestDTO offerRequestDTO);

    ApiResponse<OfferResponseDTO> updateOffer(int id, OfferRequestDTO offerRequestDTO);

    ApiResponse<Void> deleteOffer(int id);

}
