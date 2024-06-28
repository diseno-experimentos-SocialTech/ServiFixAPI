package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.TechnicalRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.request.UserRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.TechnicalService;
import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.TechnicalRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnicalServiceImpl implements TechnicalService {

    private final TechnicalRepository technicalRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public TechnicalServiceImpl(TechnicalRepository technicalRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.technicalRepository = technicalRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> getTechnicalByAccount(int account_id) {
        Optional<Account> accountOptional = accountRepository.findById(account_id);

        if (accountOptional.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        } else {
            Account account = accountOptional.get();

            Technical technical = technicalRepository.getTechnicalByAccount_Id(account_id);

            if (technical == null) {
                return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
            }else{
                TechnicalResponseDTO responseDTO = modelMapper.map(technical, TechnicalResponseDTO.class);

                responseDTO.setAccount(account);

                return new ApiResponse<>("Technical fetched successfully", Estatus.SUCCESS, responseDTO);
            }
        }
    }

    @Override
    public ApiResponse<List<TechnicalResponseDTO>> getTechnicals() {
        List<Technical> technicalList = (List<Technical>) technicalRepository.findAll();
        List<TechnicalResponseDTO> technicalResponseDTOList = technicalList.stream()
                .map(entity -> modelMapper.map(entity, TechnicalResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All technicals fetched successfully", Estatus.SUCCESS, technicalResponseDTOList);
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> getTechnicalById(int id) {
        Optional<Technical> technicalOptional = technicalRepository.findById(id);
        if (technicalOptional.isPresent()) {
            Technical technical = technicalOptional.get();
            TechnicalResponseDTO responseDTO = modelMapper.map(technical, TechnicalResponseDTO.class);
            return new ApiResponse<>("Technical fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> createTechnical(TechnicalRequestDTO technicalRequestDTO) {
        var technical = modelMapper.map(technicalRequestDTO, Technical.class);
        validateTechnical(technicalRequestDTO);
        technical.setAccount(accountRepository.getAccountById(technicalRequestDTO.getAccount()));
        technicalRepository.save(technical);

        var response = modelMapper.map(technical, TechnicalResponseDTO.class);

        return new ApiResponse<>("Technical created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> updateTechnical(int id, TechnicalRequestDTO technicalRequestDTO) {
        Optional<Technical> technicalOptional = technicalRepository.findById(id);

        if (technicalOptional.isEmpty()) {
            return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
        } else {
            Technical technical = technicalOptional.get();
            modelMapper.map(technicalRequestDTO, technical);
            validateUpdateTechnical(id, technicalRequestDTO);
            technical.setAccount(accountRepository.getAccountById(technicalRequestDTO.getAccount()));
            technicalRepository.save(technical);
            TechnicalResponseDTO response = modelMapper.map(technical, TechnicalResponseDTO.class);
            return new ApiResponse<>("Technical updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteTechnical(int id) {
        Optional<Technical> technicalOptional = technicalRepository.findById(id);

        if (technicalOptional.isEmpty()) {
            return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
        } else {
            technicalRepository.deleteById(id);
            return new ApiResponse<>("Technical deleted successfully", Estatus.SUCCESS, null);
        }
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> getTechnicalByAccount_FirstName(String firstName) {

        Technical technical = technicalRepository.getTechnicalByAccount_FirstName(firstName);

        if (technical == null) {
            return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
        } else {

            TechnicalResponseDTO responseDTO = modelMapper.map(technical, TechnicalResponseDTO.class);
            responseDTO.setAccount(accountRepository.getAccountById(technical.getAccount().getId()));

            return new ApiResponse<>("Technical fetched successfully", Estatus.SUCCESS, responseDTO);

        }
    }

    @Override
    public ApiResponse<TechnicalResponseDTO> getTechnicalByAccount_LastName(String lastName) {

        Technical technicalOptional = technicalRepository.getTechnicalByAccount_LastName(lastName);

        if (technicalOptional == null) {
            return new ApiResponse<>("Technical not found", Estatus.ERROR, null);
        } else {

            TechnicalResponseDTO responseDTO = modelMapper.map(technicalOptional, TechnicalResponseDTO.class);
            responseDTO.setAccount(accountRepository.getAccountById(technicalOptional.getAccount().getId()));

            return new ApiResponse<>("Technical fetched successfully", Estatus.SUCCESS, responseDTO);

        }
    }


    private void validateTechnical(TechnicalRequestDTO technical) {
        if (!isValidNumber(technical.getNumber())) {
            throw new ValidationException("The number provided is not valid");
        }
        if (isTechnicalNumberExists(technical.getNumber())) {
            throw new ValidationException("There is already a technician with the same technical number");
        }
        if (isPoliceRecordsExists(technical.getPoliceRecords())) {
            throw new ValidationException("There is already a technician with the same police records");
        }
        if (isExistsAccount(technical.getAccount())) {
            throw new ValidationException("The account provided does not exist");
        }
        /*
        if (!isValidateRole(technical.getAccount())) {
            throw new ValidationException("The role provided is not valid");
        }*/
    }

    private void validateUpdateTechnical(int id, TechnicalRequestDTO technical) {
        if (!isValidNumber(technical.getNumber())) {
            throw new ValidationException("The number provided is not valid");
        }
        if (isTechnicalNumberExistsById(technical.getNumber(), id)) {
            throw new ValidationException("There is already a technician with the same technical number");
        }
        if (isPoliceRecordsExistsById(technical.getPoliceRecords(),id)) {
            throw new ValidationException("There is already a technician with the same police records");
        }
        if (isExistsAccountById(technical.getAccount(), id)) {
            throw new ValidationException("The account provided does not exist");
        }
    }

    //create
    private boolean isTechnicalNumberExists(String number) {
        return technicalRepository.existsByNumber(number);
    }

    private boolean isPoliceRecordsExists(String policeRecords) {
        return technicalRepository.existsByPoliceRecords(policeRecords);
    }

    private boolean isValidNumber(String number) {
        return number != null && number.matches("9\\d{8}");
    }

    private boolean isExistsAccount(int account_id) {
        return technicalRepository.existsByAccount_Id(account_id);
    }


    //update
    private boolean isTechnicalNumberExistsById(String number, int id) {
        return technicalRepository.existsByNumberAndIdNot(number, id);
    }

    private boolean isPoliceRecordsExistsById(String policeRecords,int id) {
        return technicalRepository.existsByPoliceRecordsAndIdNot(policeRecords, id);
    }
    private boolean isExistsAccountById(int account_id, int id) {
        return technicalRepository.existsByAccount_IdAndIdNot(account_id, id);
    }


    /*
    private boolean isValidateRole(int roleId) {
        return technicalRepository.searchByAccount_Role_Id(roleId) == 2;
    }*/



}
