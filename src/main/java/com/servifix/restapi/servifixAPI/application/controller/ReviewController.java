package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.ReviewRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReviewResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.ReviewService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review", description = "Review API")
@RestController
@RequestMapping("/api/v1/servifix")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Get Review by ID")
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> getReviewById(@PathVariable("id") int id) {
        var res = reviewService.getReviewById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get Reviews by Technical ID")
    @GetMapping("/reviews/technical/{id}")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getReviewByTechnicalId(@PathVariable("id") int id) {
        var res = reviewService.getReviewByTechnicalId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Review")
    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        var res = reviewService.createReview(reviewRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing Review")
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> updateReview(@PathVariable int id, @RequestBody ReviewRequestDTO reviewRequestDTO) {
        var res = reviewService.updateReview(id, reviewRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Review")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable int id) {
        var res = reviewService.deleteReview(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
