package com.servifix.restapi.security.service;

import com.servifix.restapi.security.model.request.LoginRequestDto;
import com.servifix.restapi.security.model.request.RegisterRequestDto;
import com.servifix.restapi.security.model.response.RegisteredUserResponseDto;
import com.servifix.restapi.security.model.response.TokenResponseDto;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface AuthService {

    ApiResponse<RegisteredUserResponseDto> registerUser(RegisterRequestDto request);

    ApiResponse<TokenResponseDto> login(LoginRequestDto request);

}
