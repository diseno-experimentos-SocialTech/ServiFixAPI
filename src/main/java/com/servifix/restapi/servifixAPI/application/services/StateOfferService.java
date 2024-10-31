package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.StateOfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.StateOfferResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface StateOfferService {

    ApiResponse<StateOfferResponseDTO> getStateOfferById(int id);

    ApiResponse<StateOfferResponseDTO> createStateOffer(StateOfferRequestDTO stateOfferRequestDTO);

}
