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
public class PaymentRequestDTO {

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Amount is required")
    private double amount;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Offer is required")
    private int offer;
}
