package com.servifix.restapi.security.controller;

import com.servifix.restapi.security.model.request.LoginRequestDto;
import com.servifix.restapi.security.model.request.RegisterRequestDto;
import com.servifix.restapi.security.model.response.RegisteredUserResponseDto;
import com.servifix.restapi.security.model.response.TokenResponseDto;
import com.servifix.restapi.security.service.AuthService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@SecurityRequirements //desactiva la seguridad para este controlador (swagger)
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Inicia sesión")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        var res = service.login(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Registra un nuevo usuario
     * @param request Datos para el registro
     * @return Usuario registrado
     */
    @Operation(summary = "Registra un nuevo usuario")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisteredUserResponseDto>> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        var res = service.registerUser(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
