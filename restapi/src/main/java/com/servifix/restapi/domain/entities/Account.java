package com.servifix.restapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name = "firstName", length = 75, nullable = false)
    private String firstName;

    @Column( name = "lastName", length = 75, nullable = false)
    private String lastName;

    @Column(name = "gender", length = 50,nullable = false)
    private String gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column( name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 50, nullable = false)
    private String password;
    
}
