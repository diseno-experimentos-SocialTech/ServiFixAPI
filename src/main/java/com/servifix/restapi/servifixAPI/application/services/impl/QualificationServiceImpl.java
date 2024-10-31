package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.QualificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.QualificationResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.QualificationService;
import com.servifix.restapi.servifixAPI.domain.entities.Qualification;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.QualificationRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.ReviewRepository;
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
    public QualificationServiceImpl() {
        this.qualificationRepository = null;
        this.reviewRepository = null;
        this.offerRepository = null;
        this.modelMapper = null;
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

        if (!isValidQualification(qualificationRequestDTO.getQuality())) {
            return new ApiResponse<>("Invalid qualification", Estatus.ERROR, null);
        }
        var qualification = modelMapper.map(qualificationRequestDTO, Qualification.class);
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

    boolean isValidQualification(float qualification) {
        return qualification >= 1 && qualification <= 5;
    }

}
