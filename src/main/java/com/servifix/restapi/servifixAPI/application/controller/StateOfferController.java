package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.StateOfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.StateOfferResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.StateOfferService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StateOffer", description = "StateOffer API")
@RestController
@RequestMapping("/api/v1/servifix")
public class StateOfferController {

    private final StateOfferService stateOfferService;

    public StateOfferController(StateOfferService stateOfferService) {
        this.stateOfferService = stateOfferService;
    }

    @Operation(summary = "Get state by id")
    @GetMapping("/stateoffer/{id}")
    public ResponseEntity<ApiResponse<StateOfferResponseDTO>> getStateOfferById(@PathVariable("id") int id) {
        var res = stateOfferService.getStateOfferById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new state offer")
    @PostMapping("/stateoffer")
    public ResponseEntity<ApiResponse<StateOfferResponseDTO>> createStateOffer(@RequestBody StateOfferRequestDTO stateOfferRequestDTO) {
        var res = stateOfferService.createStateOffer(stateOfferRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
