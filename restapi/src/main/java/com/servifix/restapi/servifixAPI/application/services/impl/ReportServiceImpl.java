package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.ReportRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.ReportResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.ReportService;
import com.servifix.restapi.servifixAPI.domain.entities.Report;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.ReportRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper, OfferRepository offerRepository) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
    }

    @Override
    public ApiResponse<ReportResponseDTO> getReportById(int id) {
        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            ReportResponseDTO responseDTO = modelMapper.map(report, ReportResponseDTO.class);
            return new ApiResponse<>("Report fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Report not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<ReportResponseDTO> createReport(ReportRequestDTO reportRequestDTO) {
        var report = modelMapper.map(reportRequestDTO, Report.class);
        ValidateReport(reportRequestDTO);
        report.setOffer(offerRepository.getOfferById(reportRequestDTO.getOffer()));
        reportRepository.save(report);

        var response = modelMapper.map(report, ReportResponseDTO.class);

        return new ApiResponse<>("Report created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<ReportResponseDTO> updateReport(int id, ReportRequestDTO reportRequestDTO) {
        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            modelMapper.map(reportRequestDTO, report);
            ValidateReport(reportRequestDTO);
            report.setOffer(offerRepository.getOfferById(reportRequestDTO.getOffer()));
            reportRepository.save(report);

            var response = modelMapper.map(report, ReportResponseDTO.class);

            return new ApiResponse<>("Report updated successfully", Estatus.SUCCESS, response);
        } else {
            return new ApiResponse<>("Report not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<Void> deleteReport(int id) {
        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            reportRepository.deleteById(id);
            return new ApiResponse<>("Report deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Report not found", Estatus.ERROR, null);
        }
    }

    private void ValidateReport(ReportRequestDTO report) {
        if (!isValidateDate(report.getDate())) {
            throw new ValidationException("The date must be the current date");
        }
    }
    private boolean isValidateDate(LocalDate date) {
        return date.equals(LocalDate.now());
    }
}
