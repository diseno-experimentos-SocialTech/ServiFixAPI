package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponseDTO {

    private int id;

    private LocalDate date;

    private String observation;

    private String diagnosis;

    private Offer offer;
}
