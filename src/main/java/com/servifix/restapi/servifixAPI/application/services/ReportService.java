package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.ReportRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReportResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface ReportService {

    ApiResponse<ReportResponseDTO> getReportById(int id);

    ApiResponse<ReportResponseDTO> createReport(ReportRequestDTO reportRequestDTO);

    ApiResponse<ReportResponseDTO> updateReport(int id, ReportRequestDTO reportRequestDTO);

    ApiResponse<Void> deleteReport(int id);

}
