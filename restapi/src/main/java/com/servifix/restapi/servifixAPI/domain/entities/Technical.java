package com.servifix.restapi.servifixAPI.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "technicals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Technical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int technicalId;

    @Column(name = "police_records", length = 200, nullable = false)
    private String policeRecords;

    @Column(name = "skills", length = 200, nullable = false)
    private String skills;

    @Column(name = "experience", length = 200, nullable = false)
    private String experience;

    @Column(name = "number", length = 9, nullable = false)
    private String number;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

}