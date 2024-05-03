package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.AccountRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.AccountService;
import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.RoleRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public ApiResponse<AccountResponseDTO> createAccount(AccountRequestDTO accountRequestDTO) {

        var account = modelMapper.map(accountRequestDTO, Account.class);
        validateAccount(account);
        account.setRole(roleRepository.getRoleById(accountRequestDTO.getRole()));
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
            validateAccount(account);
            account.setRole(roleRepository.getRoleById(accountRequestDTO.getRole()));
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

    @Override
    public Account getAccountById(int id) {
        return accountRepository.getAccountById(id);
    }


    private void validateAccount(Account account) {
        if (!isValidateGender(account.getGender())) {
            throw new ValidationException("El género que se proporciono no es válido");
        }
        if (!isValidateEmail(account.getEmail())) {
            throw new ValidationException("El email que se proporciono no es válido");
        }
        if (!isAdult(account.getBirthday())) {
            throw new ValidationException("El usuario debe ser mayor de edad");
        }
        if (!isPasswordStrong(account.getPassword())) {
            throw new ValidationException("La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula y un número");
        }
        if (!isValidRole(account.getRole().getId())) {
            throw new ValidationException("El rol que se proporciono no es válido");
        }
        if (isEmailExists(account.getEmail())) {
            throw new ValidationException("El email que se proporciono ya existe");
        }
    }


    private boolean isValidateGender(String gender) {
        return gender.equals("Femenino") || gender.equals("Masculino") || gender.equals("Otro") || gender.equals("Prefiero no decirlo");
    }
    /*
    private  boolean isValidateEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    */

    private boolean isValidateEmail(String email) {
        // Expresión regular que valida las cuentas de Gmail, Outlook y Hotmail
        String regex = "^[\\w-\\.]+@(gmail\\.com|outlook\\.(com|es)|hotmail\\.com)$";
        return email.matches(regex);
    }

    private boolean isAdult(LocalDate birthday) {
        return birthday.plusYears(18).isBefore(LocalDate.now());
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    }
    private boolean isValidRole(int roleId) {
        return roleId == 1 || roleId == 2;
    }

    private boolean isEmailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

}
