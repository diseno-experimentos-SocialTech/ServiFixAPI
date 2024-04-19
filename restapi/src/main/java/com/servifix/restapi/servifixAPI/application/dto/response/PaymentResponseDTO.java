package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {

    private int id;

    private String type;

    private double amount;

    private String cardNumber;

    private Offer offer;
}
