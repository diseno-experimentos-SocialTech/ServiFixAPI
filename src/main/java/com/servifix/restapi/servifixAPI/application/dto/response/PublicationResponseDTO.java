package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Job;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationResponseDTO {

    private int id;

    private String title;

    private String description;

    private double amount;

    private String picture;

    private String address;

    private User user;

    private Job job;
}
