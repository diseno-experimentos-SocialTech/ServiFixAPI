package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.QualificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.QualificationResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.QualificationService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Qualification", description = "Qualification API")
@RestController
@RequestMapping("/api/v1/servifix")
public class QualificationController {

    private final QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @Operation(summary = "Get a qualification by ID")
    @GetMapping("/qualifications/{id}")
    public ResponseEntity<ApiResponse<QualificationResponseDTO>> getQualificationById(@PathVariable("id") int id) {
        var res = qualificationService.getQualificationById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all qualifications by review ID")
    @GetMapping("/qualifications/review/{id}")
    public ResponseEntity<ApiResponse<List<QualificationResponseDTO>>> getQualificationByReviewId(@PathVariable("id") int id) {
        var res = qualificationService.getQualificationByReviewId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all qualifications by offer ID")
    @GetMapping("/qualifications/offer/{id}")
    public ResponseEntity<ApiResponse<List<QualificationResponseDTO>>> getQualificationByOfferId(@PathVariable("id") int id) {
        var res = qualificationService.getQualificationByOfferId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new qualification")
    @PostMapping("/qualifications")
    public ResponseEntity<ApiResponse<QualificationResponseDTO>> createQualification(@RequestBody QualificationRequestDTO qualificationRequestDTO) {
        var res = qualificationService.createQualification(qualificationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing qualification")
    @PutMapping("/qualifications/{id}")
    public ResponseEntity<ApiResponse<QualificationResponseDTO>> updateQualification(@PathVariable int id, @RequestBody QualificationRequestDTO qualificationRequestDTO) {
        var res = qualificationService.updateQualification(id, qualificationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a qualification")
    @DeleteMapping("/qualifications/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQualification(@PathVariable int id) {
        var res = qualificationService.deleteQualification(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
