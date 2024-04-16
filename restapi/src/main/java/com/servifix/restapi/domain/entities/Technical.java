package com.servifix.restapi.domain.entities;

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

}