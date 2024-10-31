package com.servifix.restapi.servifixAPI.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "review")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "average", nullable = false)
    private float average;

    @Column(name = "feature_Comment", length = 200, nullable = false)
    private String featureComment;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technical_id", nullable = false)
    private Technical technical;
}
