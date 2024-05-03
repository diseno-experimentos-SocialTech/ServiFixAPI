package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.TechnicalRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.TechnicalService;
import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.TechnicalRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
            validateTechnical(technicalRequestDTO);
            modelMapper.map(technicalRequestDTO, technical);
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
        if (!isExistsAccount(technical.getAccount())) {
            throw new ValidationException("The account provided does not exist");
        }
        /*
        if (!isValidateRole(technical.getAccount())) {
            throw new ValidationException("The role provided is not valid");
        }*/
    }

    private boolean isTechnicalNumberExists(String number) {
        return technicalRepository.existsByNumber(number);
    }

    private boolean isPoliceRecordsExists(String policeRecords) {
        return technicalRepository.existsByPoliceRecords(policeRecords);
    }

    private boolean isValidNumber(String number) {
        return number != null && number.length() == 9 && number.matches("\\d{9}");
    }

    private boolean isExistsAccount(int accountId) {
        return technicalRepository.existsByAccount_Id(accountId);
    }

    /*
    private boolean isValidateRole(int roleId) {
        return technicalRepository.searchByAccount_Role_Id(roleId) == 2;
    }*/

}
