package com.servifix.restapi.servifixAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRequestDTO {

    @NotBlank(message = "The date is required")
    private LocalDate date;

    @NotBlank(message = "The observation is required")
    private String observation;

    @NotBlank(message = "The diagnosis is required")
    private String diagnosis;

    @NotBlank(message = "The offer is required")
    private int offer;

}
