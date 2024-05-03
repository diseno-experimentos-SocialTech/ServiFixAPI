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
public class UserRequestDTO {

    @NotBlank(message = "address is mandatory")
    private String address;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotBlank(message = "image is mandatory")
    private String image;

    @NotBlank(message = "number is mandatory")
    private String number;

    @NotBlank(message = "accountId is mandatory")
    private int account;

}
