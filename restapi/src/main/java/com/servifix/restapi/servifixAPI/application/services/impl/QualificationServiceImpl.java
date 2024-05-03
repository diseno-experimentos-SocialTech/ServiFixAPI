package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.QualificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.QualificationResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.QualificationService;
import com.servifix.restapi.servifixAPI.domain.entities.Qualification;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.QualificationRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.ReviewRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final ReviewRepository reviewRepository;
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public QualificationServiceImpl(QualificationRepository qualificationRepository, ReviewRepository reviewRepository, OfferRepository offerRepository, ModelMapper modelMapper) {
        this.qualificationRepository = qualificationRepository;
        this.reviewRepository = reviewRepository;
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<QualificationResponseDTO> getQualificationById(int id) {
        Optional<Qualification> qualificationOptional = qualificationRepository.findById(id);

        if (qualificationOptional.isPresent()) {
            Qualification qualification = qualificationOptional.get();
            QualificationResponseDTO responseDTO = modelMapper.map(qualification, QualificationResponseDTO.class);
            return new ApiResponse<>("Qualification fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Qualification not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<List<QualificationResponseDTO>> getQualificationByReviewId(int reviewId) {
        List<Qualification> qualificationList = qualificationRepository.getQualificationByReviewId(reviewId);
        List<QualificationResponseDTO> qualificationResponseDTOList = qualificationList.stream()
                .map(entity -> modelMapper.map(entity, QualificationResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());
        return new ApiResponse<>("Qualifications fetched successfully", Estatus.SUCCESS, qualificationResponseDTOList);
    }

    @Override
    public ApiResponse<List<QualificationResponseDTO>> getQualificationByOfferId(int offerId) {
        List<Qualification> qualificationList = qualificationRepository.getQualificationByOfferId(offerId);
        List<QualificationResponseDTO> qualificationResponseDTOList = qualificationList.stream()
                .map(entity -> modelMapper.map(entity, QualificationResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());
        return new ApiResponse<>("Qualifications fetched successfully", Estatus.SUCCESS, qualificationResponseDTOList);
    }

    @Override
    public ApiResponse<QualificationResponseDTO> createQualification(QualificationRequestDTO qualificationRequestDTO) {
        var qualification = modelMapper.map(qualificationRequestDTO, Qualification.class);
        validateQualification(qualificationRequestDTO);
        qualification.setReview(reviewRepository.getReviewById(qualificationRequestDTO.getReview()));
        qualification.setOffer(offerRepository.getOfferById(qualificationRequestDTO.getOffer()));
        qualificationRepository.save(qualification);
        var response = modelMapper.map(qualification, QualificationResponseDTO.class);
        return new ApiResponse<>("Qualification created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<QualificationResponseDTO> updateQualification(int id, QualificationRequestDTO qualificationRequestDTO) {
        Optional<Qualification> qualificationOptional = qualificationRepository.findById(id);

        if (qualificationOptional.isPresent()) {
            Qualification qualification = qualificationOptional.get();
            modelMapper.map(qualificationRequestDTO, qualification);
            validateQualification(qualificationRequestDTO);
            qualification.setReview(reviewRepository.getReviewById(qualificationRequestDTO.getReview()));
            qualification.setOffer(offerRepository.getOfferById(qualificationRequestDTO.getOffer()));
            qualificationRepository.save(qualification);
            var response = modelMapper.map(qualification, QualificationResponseDTO.class);
            return new ApiResponse<>("Qualification updated successfully", Estatus.SUCCESS, response);
        } else {
            return new ApiResponse<>("Qualification not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<Void> deleteQualification(int id) {
        Optional<Qualification> qualificationOptional = qualificationRepository.findById(id);

        if (qualificationOptional.isPresent()) {
            qualificationRepository.deleteById(id);
            return new ApiResponse<>("Qualification deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Qualification not found", Estatus.ERROR, null);
        }
    }

    private void validateQualification(QualificationRequestDTO qualification) {
        if (!isValidateQuality(qualification.getQuality())) {
            throw new ValidationException("The quality must be between 0 and 5");
        }
        if (!isValidateRelevance(qualification.getRelevance())) {
            throw new ValidationException("The relevance must be between 0 and 5");
        }
    }


    private boolean isValidateQuality(float quality) {
        return quality >= 0 && quality <= 5;
    }
    private boolean isValidateRelevance(float relevance) {
        return relevance >= 0 && relevance <= 5;
    }

}
