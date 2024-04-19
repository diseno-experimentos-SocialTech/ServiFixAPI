package com.servifix.restapi.servifixAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateOfferRequestDTO {

    @NotBlank(message = "State is mandatory")
    private String state;


}
