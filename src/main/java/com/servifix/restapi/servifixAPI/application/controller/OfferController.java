package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.OfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.OfferResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.OfferService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Offer", description = "Offer API")
@RestController
@RequestMapping("/api/v1/servifix")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Get offer by ID")
    @GetMapping("/offers/{id}")
    public ResponseEntity<ApiResponse<OfferResponseDTO>> getOfferById(@PathVariable("id") int id) {
        ApiResponse<OfferResponseDTO> response = offerService.getOfferById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get offers by TechnicalId")
    @GetMapping("/offers/technical/{id}")
    public ResponseEntity<ApiResponse<List<OfferResponseDTO>>> getOffersByTechnicalId(@PathVariable("id") int id) {
        var res = offerService.getOffersByTechnicalId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get offers by PublicationId")
    @GetMapping("/offers/publication/{id}")
    public ResponseEntity<ApiResponse<List<OfferResponseDTO>>> getOffersByPublicationId(@PathVariable("id") int id) {
        var res = offerService.getOffersByPublicationId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get offers by PublicationId And StateOfferId")
    @GetMapping("/offers/publication/{publicationId}/stateoffer/{stateOfferId}")
    public ResponseEntity<ApiResponse<List<OfferResponseDTO>>> getOffersByPublicationIdAndStateOfferId(@PathVariable("publicationId") int publicationId, @PathVariable("stateOfferId") int stateOfferId) {
        var res = offerService.getOffersByPublicationIdAndStateOfferId(publicationId, stateOfferId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new offer")
    @PostMapping("/offers")
    public ResponseEntity<ApiResponse<OfferResponseDTO>> createOffer(@RequestBody OfferRequestDTO offerRequestDTO) {
        var res = offerService.createOffer(offerRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing offer")
    @PutMapping("/offers/{id}")
    public ResponseEntity<ApiResponse<OfferResponseDTO>> updateOffer(@PathVariable int id, @RequestBody OfferRequestDTO offerRequestDTO) {
        var res = offerService.updateOffer(id, offerRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete an offer")
    @DeleteMapping("/offers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOffer(@PathVariable int id) {
        var res = offerService.deleteOffer(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
