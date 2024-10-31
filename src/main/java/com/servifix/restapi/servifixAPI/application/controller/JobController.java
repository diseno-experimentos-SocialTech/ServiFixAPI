package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.JobRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.JobResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.JobService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Job", description = "Job API")
@RestController
@RequestMapping("/api/v1/servifix")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(summary = "Get all jobs")
    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<JobResponseDTO>>> getAllJobs() {
        var res = jobService.getJobs();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation
    @GetMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> getJobById(@PathVariable("id") int id) {
        ApiResponse<JobResponseDTO> response = jobService.getJobById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a new job")
    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse<JobResponseDTO>> createJob(@RequestBody JobRequestDTO jobRequestDTO) {
        var res = jobService.createJob(jobRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing job")
    @PutMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<JobResponseDTO>> updateJob(@PathVariable int id, @RequestBody JobRequestDTO jobRequestDTO) {
        var res = jobService.updateJob(id, jobRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Delete a job")
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteJob(@PathVariable int id) {
        var res = jobService.deleteJob(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
