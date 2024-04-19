package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.QualificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.QualificationResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface QualificationService {

    ApiResponse<QualificationResponseDTO> getQualificationById(int id);

    ApiResponse<List<QualificationResponseDTO>> getQualificationByReviewId(int reviewId);

    ApiResponse<List<QualificationResponseDTO>> getQualificationByOfferId(int offerId);

    ApiResponse<QualificationResponseDTO> createQualification(QualificationRequestDTO qualificationRequestDTO);

    ApiResponse<Void> deleteQualification(int id);

    ApiResponse<QualificationResponseDTO> updateQualification(int id, QualificationRequestDTO qualificationRequestDTO);
}
