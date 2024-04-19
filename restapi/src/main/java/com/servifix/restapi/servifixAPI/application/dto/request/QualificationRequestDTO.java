package com.servifix.restapi.servifixAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualificationRequestDTO {

    @NotBlank(message = "quality is required")
    private float quality;

    @NotBlank(message = "comment is required")
    private String comment;

    @NotBlank(message = "relevance is required")
    private float relevance;

    @NotBlank(message = "review is required")
    private int review;

    @NotBlank(message = "offer is required")
    private int offer;

}
