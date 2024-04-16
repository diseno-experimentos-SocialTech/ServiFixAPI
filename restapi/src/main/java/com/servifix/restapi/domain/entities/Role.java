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
public class Role {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "type", length = 15, nullable = false)
    private String type;

}
