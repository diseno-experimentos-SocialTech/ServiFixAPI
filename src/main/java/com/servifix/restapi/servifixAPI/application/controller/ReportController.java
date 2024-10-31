package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.ReportRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReportResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.ReportService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Report", description = "Report API")
@RestController
@RequestMapping("/api/v1/servifix")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get a report by ID")
    @GetMapping("/reports/{id}")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> getReportById(@PathVariable("id") int id) {
        var res = reportService.getReportById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Create a new report")
    @PostMapping("/reports")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> createReport(@RequestBody ReportRequestDTO reportRequestDTO) {
        var res = reportService.createReport(reportRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing report")
    @PutMapping("/reports/{id}")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> updateReport(@PathVariable int id, @RequestBody ReportRequestDTO reportRequestDTO) {
        var res = reportService.updateReport(id, reportRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a report")
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReport(@PathVariable int id) {
        var res = reportService.deleteReport(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
