package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.StateOfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.StateOfferResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.StateOfferService;
import com.servifix.restapi.servifixAPI.domain.entities.StateOffer;
import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.StateOfferRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateOfferImpl implements StateOfferService {

    private final StateOfferRepository stateOfferRepository;

    private final ModelMapper modelMapper;


    public StateOfferImpl(StateOfferRepository stateOfferRepository, ModelMapper modelMapper) {
        this.stateOfferRepository = stateOfferRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<StateOfferResponseDTO> getStateOfferById(int id) {
        Optional<StateOffer> stateOfferOptional = stateOfferRepository.findById(id);

        if (stateOfferOptional.isPresent()) {
            StateOffer stateOffer = stateOfferOptional.get();
            StateOfferResponseDTO responseDTO = modelMapper.map(stateOffer, StateOfferResponseDTO.class);
            return new ApiResponse<>("StateOffer fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("StateOffer not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<StateOfferResponseDTO> createStateOffer(StateOfferRequestDTO stateOfferRequestDTO) {
        var stateOffer = modelMapper.map(stateOfferRequestDTO, StateOffer.class);
        validateStateOffer(stateOfferRequestDTO);
        stateOfferRepository.save(stateOffer);
        var response = modelMapper.map(stateOffer, StateOfferResponseDTO.class);
        return new ApiResponse<>("StateOffer created successfully", Estatus.SUCCESS, response);
    }

    private void validateStateOffer(StateOfferRequestDTO stateOffer) {
        if (stateOfferRepository.existsByState(stateOffer.getState())) {
            throw new ValidationException("That state already exists");
        }
    }

}
