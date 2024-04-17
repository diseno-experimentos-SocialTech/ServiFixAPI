package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.AccountRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.AccountService;
import com.servifix.restapi.servifixAPI.application.services.RoleService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "Account API")
@RestController
@RequestMapping("/api/v1/servifix")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService ) {
        this.accountService = accountService;
    }

    @Operation(summary = "create a new account")
    @PostMapping("/accounts")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {

        var res = accountService.createAccount(accountRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "update an existing account")
    @PutMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> updateAccount(@PathVariable int id, @RequestBody AccountRequestDTO accountRequestDTO) {
        var res = accountService.updateAccount(id, accountRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "delete an account")
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable int id) {
        var res = accountService.deleteAccount(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
