package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.JobRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.JobResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface JobService {

    ApiResponse<JobResponseDTO> getJobById(int id);

    ApiResponse<List<JobResponseDTO>> getJobs();

    ApiResponse<JobResponseDTO> createJob(JobRequestDTO jobRequestDTO);

    ApiResponse<JobResponseDTO> updateJob(int id, JobRequestDTO jobRequestDTO);

    ApiResponse<Void> deleteJob(int id);
}
