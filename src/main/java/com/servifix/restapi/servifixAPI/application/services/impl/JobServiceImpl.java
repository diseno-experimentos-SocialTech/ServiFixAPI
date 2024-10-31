package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.JobRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.JobResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.JobService;
import com.servifix.restapi.servifixAPI.domain.entities.Job;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.JobRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final ModelMapper modelMapper;

    public JobServiceImpl(JobRepository jobRepository, ModelMapper modelMapper) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<JobResponseDTO> getJobById(int id){
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            JobResponseDTO responseDTO = modelMapper.map(job, JobResponseDTO.class);
            return new ApiResponse<>("Job fetched successfully", Estatus.SUCCESS, responseDTO);
        }else {
            return new ApiResponse<>("Job not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<List<JobResponseDTO>> getJobs(){
        List<Job> jobList = (List<Job>) jobRepository.findAll();
        List<JobResponseDTO> jobDTOList = jobList.stream()
                .map(entity -> modelMapper.map(entity, JobResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All jobs fetched successfully", Estatus.SUCCESS, jobDTOList);
    }

    @Override
    public ApiResponse<JobResponseDTO> createJob(JobRequestDTO jobRequestDTO){
        var job = modelMapper.map(jobRequestDTO, Job.class);
        jobRepository.save(job);

        var response = modelMapper.map(job, JobResponseDTO.class);

        return new ApiResponse<>("Job created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<JobResponseDTO> updateJob(int id, JobRequestDTO jobRequestDTO){
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isEmpty()) {
            return new ApiResponse<>("Job not found", Estatus.ERROR, null);
        }else {
            Job job = jobOptional.get();
            modelMapper.map(jobRequestDTO, job);
            jobRepository.save(job);
            JobResponseDTO response = modelMapper.map(job, JobResponseDTO.class);
            return new ApiResponse<>("Job updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteJob(int id){
        jobRepository.deleteById(id);
        return new ApiResponse<>("Job deleted successfully", Estatus.SUCCESS, null);
    }
}
