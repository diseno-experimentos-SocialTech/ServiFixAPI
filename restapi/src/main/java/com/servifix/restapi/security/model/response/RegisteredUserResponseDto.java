package com.servifix.restapi.security.model.response;

import com.servifix.restapi.servifixAPI.domain.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserResponseDto {

    private int id;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthday;

    private String email;

    private String password;

    private Role role;
}
