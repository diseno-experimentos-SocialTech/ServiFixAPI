package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.ReviewRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.OfferResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReviewResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.ReviewService;
import com.servifix.restapi.servifixAPI.domain.entities.*;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.ReviewRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.TechnicalRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private TechnicalRepository technicalRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, TechnicalRepository technicalRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.technicalRepository = technicalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<ReviewResponseDTO> getReviewById(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            ReviewResponseDTO responseDTO = modelMapper.map(review, ReviewResponseDTO.class);
            return new ApiResponse<>("Review fetched successfully", Estatus.SUCCESS,responseDTO);
        } else {
            return new ApiResponse<>("Review not found", Estatus.ERROR,null);
        }
    }

    @Override
    public ApiResponse<List<ReviewResponseDTO>> getReviewByTechnicalId(int technicalId) {
        List<Review> reviewList = reviewRepository.getReviewByTechnicalId(technicalId);
        List<ReviewResponseDTO> reviewResponseDTOList = reviewList.stream()
                .map(entity -> modelMapper.map(entity, ReviewResponseDTO.class))
                .collect(java.util.stream.Collectors.toList());
        return new ApiResponse<>("Reviews fetched successfully", Estatus.SUCCESS, reviewResponseDTOList);
    }

    @Override
    public ApiResponse<ReviewResponseDTO> createReview(ReviewRequestDTO reviewRequestDTO) {
        var review = modelMapper.map(reviewRequestDTO, Review.class);
        validateReview(reviewRequestDTO);
        review.setTechnical(technicalRepository.getTechnicalById(reviewRequestDTO.getTechnical()));
        reviewRepository.save(review);
        var response = modelMapper.map(review, ReviewResponseDTO.class);
        return new ApiResponse<>("Review created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<ReviewResponseDTO> updateReview(int id, ReviewRequestDTO reviewRequestDTO) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isEmpty()) {
            return new ApiResponse<>("Review not found", Estatus.ERROR, null);
        } else {
            Review review = reviewOptional.get();
            modelMapper.map(reviewRequestDTO, review);
            validateReview(reviewRequestDTO);
            reviewRepository.save(review);
            ReviewResponseDTO response = modelMapper.map(review, ReviewResponseDTO.class);
            return new ApiResponse<>("Review updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteReview(int id) {
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if(reviewOptional.isEmpty()) {
            return new ApiResponse<>("Review not found", Estatus.ERROR, null);
        } else {
            reviewRepository.deleteById(id);
            return new ApiResponse<>("Review deleted successfully", Estatus.SUCCESS, null);
        }
    }

    private void validateReview(ReviewRequestDTO review) {
        if (!isValidateAverage(review.getAverage())) {
            throw new ValidationException("The average must be between 0 and 5");
        }
    }
    private boolean isValidateAverage(float average) {
        return average >= 0 && average <= 5;
    }

}
