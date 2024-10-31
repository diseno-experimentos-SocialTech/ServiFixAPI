package com.servifix.restapi.servifixAPI.application.dto.response;

import com.servifix.restapi.servifixAPI.domain.entities.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDTO {

    private int id;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthday;

    private String email;

    private String password;

    private Role role;
}
