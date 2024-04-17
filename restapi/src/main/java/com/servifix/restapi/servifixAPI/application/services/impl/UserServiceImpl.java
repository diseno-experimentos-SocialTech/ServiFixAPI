package com.servifix.restapi.servifixAPI.application.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.servifix.restapi.servifixAPI.application.dto.request.UserRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.AccountService;
import com.servifix.restapi.servifixAPI.application.services.UserService;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.UserRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public ApiResponse<List<UserResponseDTO>> getUsers(){
        List<User>usersList = (List<User>) userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList =usersList.stream()
                .map(entity -> modelMapper.map(entity, UserResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All users fetched successfully", Estatus.SUCCESS,userResponseDTOList);
    }

    @Override
    public ApiResponse<UserResponseDTO> getUserById(int id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            UserResponseDTO responseDTO = modelMapper.map(user, UserResponseDTO.class);
            return new ApiResponse<>("User fetched successfully", Estatus.SUCCESS, responseDTO);
        }else {
            return new ApiResponse<>("User not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<UserResponseDTO> createUser(UserRequestDTO userRequestDTO){
        var user = modelMapper.map(userRequestDTO, User.class);
        user.setAccount(accountRepository.getAccountById(userRequestDTO.getAccount()));
        userRepository.save(user);

        var response = modelMapper.map(user, UserResponseDTO.class);

        return new ApiResponse<>("User created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<UserResponseDTO> updateUser(int id, UserRequestDTO userRequestDTO){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()){
            return new ApiResponse<>("User not found", Estatus.ERROR, null);
        }else {
            User user = userOptional.get();
            modelMapper.map(userRequestDTO, user);
            user.setAccount(accountRepository.getAccountById(userRequestDTO.getAccount()));
            userRepository.save(user);
            UserResponseDTO response = modelMapper.map(user, UserResponseDTO.class);
            return new ApiResponse<>("User updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deleteUser(int id){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()){
            return new ApiResponse<>("User not found", Estatus.ERROR, null);
        }else {
            userRepository.deleteById(id);
            return new ApiResponse<>("User deleted successfully", Estatus.SUCCESS, null);
        }
    }

}
