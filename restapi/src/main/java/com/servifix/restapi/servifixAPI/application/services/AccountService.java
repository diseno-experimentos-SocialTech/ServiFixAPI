package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.AccountRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface AccountService {

    ApiResponse<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO);

    ApiResponse<AccountResponseDTO> updateAccount(int id, AccountRequestDTO accountRequestDTO);

    ApiResponse<Void> deleteAccount(int id);



}
