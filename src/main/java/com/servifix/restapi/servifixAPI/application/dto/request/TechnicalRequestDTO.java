package com.servifix.restapi.servifixAPI.application.dto.request;

import com.servifix.restapi.servifixAPI.domain.entities.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicalRequestDTO {

    @NotBlank(message = "Police records is mandatory")
    private String policeRecords;

    @NotBlank(message = "Skills is mandatory")
    private String skills;

    @NotBlank(message = "Experience is mandatory")
    private String experience;

    @NotBlank(message = "Number is mandatory")
    private String number;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Account is mandatory")
    private int account;

}
