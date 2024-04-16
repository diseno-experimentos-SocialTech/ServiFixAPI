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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "image", length = 200, nullable = false)
    private String image;

    @Column(name = "number", length = 9, nullable = false)
    private int number;

}
