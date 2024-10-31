package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.PublicationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PublicationResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.PublicationService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Publication", description = "Publication API")
@RestController
@RequestMapping("/api/v1/servifix")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @Operation(summary = "Get all publications")
    @GetMapping("/publications")
    public ResponseEntity<ApiResponse<List<PublicationResponseDTO>>> getAllPublications() {
        var res = publicationService.getPublications();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get all publications by user")
    @GetMapping("/publications/user/{id}")
    public ResponseEntity<ApiResponse<List<PublicationResponseDTO>>> getPublicationsByUser(@PathVariable("id") int id) {
        var res = publicationService.getPublicationByUserId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get a publication by ID")
    @GetMapping("/publications/{id}")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> getPublicationById(@PathVariable("id") int id) {
        var res = publicationService.getPublicationById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new publication")
    @PostMapping("/publications")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> createPublication(@RequestBody PublicationRequestDTO publicationRequestDTO) {
        var res = publicationService.createPublication(publicationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing publication")
    @PutMapping("/publications/{id}")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> updatePublication(@PathVariable int id, @RequestBody PublicationRequestDTO publicationRequestDTO) {
        var res = publicationService.updatePublication(id, publicationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a publication")
    @DeleteMapping("/publications/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePublication(@PathVariable int id) {
        var res = publicationService.deletePublication(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
