package com.servifix.restapi.servifixAPI.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Offer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "availability", length = 50,nullable = false)
    private String availability;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technical_id", nullable = false)
    private Technical technical;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StateOffer_id", nullable = false)
    private StateOffer stateOffer;

}
