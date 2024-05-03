package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.OfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.OfferResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PublicationResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.OfferService;
import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import com.servifix.restapi.servifixAPI.domain.entities.Publication;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.PublicationRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.StateOfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.TechnicalRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final TechnicalRepository technicalRepository;

    private final PublicationRepository publicationRepository;

    private final StateOfferRepository stateOfferRepository;

    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, TechnicalRepository technicalRepository, PublicationRepository publicationRepository, StateOfferRepository stateOfferRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.technicalRepository = technicalRepository;
        this.publicationRepository = publicationRepository;
        this.stateOfferRepository = stateOfferRepository;
    }

    @Override
    public ApiResponse<OfferResponseDTO> getOfferById(int id) {
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if(offerOptional.isPresent()) {
            Offer offer = offerOptional.get();
            OfferResponseDTO responseDTO = modelMapper.map(offer, OfferResponseDTO.class);
            return new ApiResponse<>("Offer fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Offer not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<List<OfferResponseDTO>> getOffersByTechnicalId(int technicalId) {
        List<Offer> offerList = offerRepository.getOfferByTechnicalId(technicalId);
        List<OfferResponseDTO> offerResponseDTOList = offerList.stream()
                .map(entity -> modelMapper.map(entity, OfferResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());

        return new ApiResponse<>("Offers fetched successfully", Estatus.SUCCESS, offerResponseDTOList);
    }

    @Override
    public ApiResponse<List<OfferResponseDTO>> getOffersByPublicationId(int publicationId) {
        List<Offer> offerList = offerRepository.getOfferByPublicationId(publicationId);
        List<OfferResponseDTO> offerResponseDTOList = offerList.stream()
                .map(entity -> modelMapper.map(entity, OfferResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());

        return new ApiResponse<>("Offers fetched successfully", Estatus.SUCCESS, offerResponseDTOList);
    }

    @Override
    public ApiResponse<List<OfferResponseDTO>> getOffersByPublicationIdAndStateOfferId(int publicationId, int stateId) {
        List<Offer> offerList = offerRepository.getOfferByPublicationIdAndStateOfferId(publicationId, stateId);
        List<OfferResponseDTO> offerResponseDTOList = offerList.stream()
                .map(entity -> modelMapper.map(entity, OfferResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());

        return new ApiResponse<>("Offers fetched successfully", Estatus.SUCCESS, offerResponseDTOList);
    }

    @Override
    public ApiResponse<OfferResponseDTO> createOffer(OfferRequestDTO offerRequestDTO) {
        var offer = modelMapper.map(offerRequestDTO, Offer.class);
        ValidateOffer(offerRequestDTO);
        offer.setStateOffer(stateOfferRepository.getStateOfferById(offerRequestDTO.getStateOffer()));
        offer.setTechnical(technicalRepository.getTechnicalById(offerRequestDTO.getTechnical()));
        offer.setPublication(publicationRepository.getPublicationById(offerRequestDTO.getPublication()));
        offerRepository.save(offer);

        var response = modelMapper.map(offer, OfferResponseDTO.class);

        return new ApiResponse<>("Offer created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<OfferResponseDTO> updateOffer(int id, OfferRequestDTO offerRequestDTO) {
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if(offerOptional.isPresent()) {
            Offer offer = offerOptional.get();
            modelMapper.map(offerRequestDTO, offer);
            ValidateUpdateOffer(id, offerRequestDTO);
            offer.setStateOffer(stateOfferRepository.getStateOfferById(offerRequestDTO.getStateOffer()));
            offer.setTechnical(technicalRepository.getTechnicalById(offerRequestDTO.getTechnical()));
            offer.setPublication(publicationRepository.getPublicationById(offerRequestDTO.getPublication()));
            offerRepository.save(offer);

            OfferResponseDTO responseDTO = modelMapper.map(offer, OfferResponseDTO.class);
            return new ApiResponse<>("Offer updated successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Offer not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<Void> deleteOffer(int id) {
        Optional<Offer> offerOptional = offerRepository.findById(id);
        if(offerOptional.isPresent()) {
            offerRepository.deleteById(id);
            return new ApiResponse<>("Offer deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Offer not found", Estatus.ERROR, null);
        }
    }

    private void ValidateOffer(OfferRequestDTO offerRequestDTO) {
        if(isExistsOfferByTechnicalByPublication(offerRequestDTO.getTechnical(), offerRequestDTO.getPublication())) {
            throw new ValidationException("There is already an offer with the same technician and publication");
        }
        if(!isValidateAmount(offerRequestDTO.getAmount())) {
            throw new ValidationException("The amount must be greater than 0");
        }

    }
    private void ValidateUpdateOffer(int id, OfferRequestDTO offerRequestDTO) {
        if(isExistsOfferByTechnicalByPublicationById(offerRequestDTO.getTechnical(), offerRequestDTO.getPublication(), id)) {
            throw new ValidationException("There is already an offer with the same technician and publication");
        }
        if(!isValidateAmount(offerRequestDTO.getAmount())) {
            throw new ValidationException("The amount must be greater than 0");
        }
    }

    private boolean isExistsOfferByTechnicalByPublication(int technicalId, int publicationId) {
        return offerRepository.existsByTechnical_IdAndPublication_Id(technicalId, publicationId);
    }
    private boolean isExistsOfferByTechnicalByPublicationById(int technicalId, int publicationId, int id) {
        return offerRepository.existsByTechnical_IdAndPublication_IdAndIdNot(technicalId, publicationId, id);
    }
    private boolean isValidateAmount(double amount) {
        return amount > 0;
    }

}
