package com.servifix.restapi.servifixAPI.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Amount is required")
    private double amount;

    @NotBlank(message = "Picture is required")
    private String picture;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "User is required")
    private int user;

    @NotNull(message = "Job is required")
    private int job;
}
