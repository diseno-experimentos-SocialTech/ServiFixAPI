package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Publication;
import com.servifix.restapi.servifixAPI.domain.entities.StateOffer;
import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponseDTO {

    private int id;

    private String availability;

    private double amount;

    private String description;

    private Technical technical;

    private Publication publication;

    private StateOffer stateOffer;

}
