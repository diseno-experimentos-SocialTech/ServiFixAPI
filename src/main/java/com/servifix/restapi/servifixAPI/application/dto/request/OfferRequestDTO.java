package com.servifix.restapi.servifixAPI.application.dto.request;

import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferRequestDTO {

    @NotBlank(message = "availability is required")
    private String availability;

    @NotBlank(message = "amount is required")
    private double amount;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "technical is required")
    private int technical;

    @NotBlank(message = "publication is required")
    private int publication;

    @NotBlank(message = "stateOffer is required")
    private int stateOffer;


}
