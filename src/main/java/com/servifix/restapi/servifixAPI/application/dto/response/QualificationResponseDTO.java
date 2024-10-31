package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import com.servifix.restapi.servifixAPI.domain.entities.Review;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualificationResponseDTO {

    private int id;

    private float quality;

    private String comment;

    private float relevance;

    private Review review;

    private Offer offer;

}
