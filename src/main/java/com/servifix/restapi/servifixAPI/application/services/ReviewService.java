package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.ReviewRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReviewResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface ReviewService {

    ApiResponse<ReviewResponseDTO> getReviewById(int id);

    ApiResponse<List<ReviewResponseDTO>> getReviewByTechnicalId(int technicalId);

    ApiResponse<ReviewResponseDTO> createReview(ReviewRequestDTO reviewRequestDTO);

    ApiResponse<ReviewResponseDTO> updateReview(int id, ReviewRequestDTO reviewRequestDTO);

    ApiResponse<Void> deleteReview(int id);
}
