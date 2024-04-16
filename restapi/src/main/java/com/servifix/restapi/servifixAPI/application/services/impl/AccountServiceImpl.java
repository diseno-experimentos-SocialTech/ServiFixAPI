package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.AccountRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.AccountService;
import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleServiceImpl roleServiceImpl;
    private final ModelMapper modelMapper;


    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper, RoleServiceImpl roleServiceImpl) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.roleServiceImpl = roleServiceImpl;
    }

    @Override
    public ApiResponse<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO) {

        var account = modelMapper.map(accountRequestDTO, Account.class);
        account.setRole(roleServiceImpl.getRoleById(accountRequestDTO.getRole()));
        accountRepository.save(account);

        var response = modelMapper.map(account, AccountResponseDTO.class);

        return new ApiResponse<>("Account created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<AccountResponseDTO> updateAccount(int id, AccountRequestDTO accountRequestDTO) {

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        }else {
            Account account = accountOptional.get();
            modelMapper.map(accountRequestDTO, account);
            accountRepository.save(account);
            AccountResponseDTO response = modelMapper.map(account, AccountResponseDTO.class);
            return new ApiResponse<>("Account updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteAccount(int id) {

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        }else {
            accountRepository.deleteById(id);
            return new ApiResponse<>("Account deleted successfully", Estatus.SUCCESS, null);
        }
    }

}
