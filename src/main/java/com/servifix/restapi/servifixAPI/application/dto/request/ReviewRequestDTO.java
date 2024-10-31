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
public class ReviewRequestDTO {

    @NotBlank(message = "Average is required")
    private float average;

    @NotBlank(message = "Feature comment is required")
    private String featureComment;

    @NotBlank(message = "Technical is required")
    private int technical;
}
