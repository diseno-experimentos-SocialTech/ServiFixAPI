package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.TechnicalRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.TechnicalService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.util.List;

@Tag(name = "Technical", description = "Technical API")
@RestController
@RequestMapping("/api/v1/servifix")
public class TechnicalController {
    private final TechnicalService technicalService;

    public TechnicalController(TechnicalService technicalService) {
        this.technicalService = technicalService;
    }

    @Operation(summary = "get all technicals")
    @GetMapping("/technicals")
    public ResponseEntity<ApiResponse<List<TechnicalResponseDTO>>> getAllTechnicals() {
        var res = technicalService.getTechnicals();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "get a technical by account")
    @GetMapping("/technicals/account/{account_id}")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> getTechnicalByAccount(@PathVariable("account_id") int account_id) {
        ApiResponse<TechnicalResponseDTO> response = technicalService.getTechnicalByAccount(account_id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "get a technical by id")
    @GetMapping("/technicals/{id}")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> getTechnicalById(@PathVariable("id") int id) {
        ApiResponse<TechnicalResponseDTO> response = technicalService.getTechnicalById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "create a new technical")
    @PostMapping("/technicals")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> createTechnical(@RequestBody TechnicalRequestDTO technicalRequestDTO) {
        var res = technicalService.createTechnical(technicalRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "update an existing technical")
    @PutMapping("/technicals/{id}")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> updateTechnical(@PathVariable int id, @RequestBody TechnicalRequestDTO technicalRequestDTO) {
        var res = technicalService.updateTechnical(id, technicalRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "delete a technical")
    @DeleteMapping("/technicals/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTechnical(@PathVariable int id) {
        var res = technicalService.deleteTechnical(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "get a technical by first name")
    @GetMapping("/technicals/firstName/{firstName}")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> getTechnicalByAccount_FirstName(@PathVariable("firstName") String firstName) {
        ApiResponse<TechnicalResponseDTO> response = technicalService.getTechnicalByAccount_FirstName(firstName);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "get a technical by last name")
    @GetMapping("/technicals/lastName/{lastName}")
    public ResponseEntity<ApiResponse<TechnicalResponseDTO>> getTechnicalByAccount_LastName(@PathVariable("lastName") String lastName) {
        ApiResponse<TechnicalResponseDTO> response = technicalService.getTechnicalByAccount_LastName(lastName);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
