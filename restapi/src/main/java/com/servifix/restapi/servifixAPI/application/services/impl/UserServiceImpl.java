package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.UserRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.UserService;
import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.UserRepository;
import com.servifix.restapi.shared.exception.ValidationException;
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
    public ApiResponse<UserResponseDTO> getUserByAccount(int account_id) {
        Optional<Account> accountOptional = accountRepository.findById(account_id);

        if (accountOptional.isEmpty()) {
            return new ApiResponse<>("Account not found", Estatus.ERROR, null);
        } else {
            Account account = accountOptional.get();

            User user = userRepository.getUserByAccount_Id(account_id);

            if (user == null) {
                return new ApiResponse<>("User not found", Estatus.ERROR, null);
            }else{
                UserResponseDTO responseDTO = modelMapper.map(user, UserResponseDTO.class);

                responseDTO.setAccount(account);

                return new ApiResponse<>("User fetched successfully", Estatus.SUCCESS, responseDTO);
            }
        }
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
        validateUser(userRequestDTO);
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
            validateUpdateUser(id, userRequestDTO);
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

    private void validateUser(UserRequestDTO user) {
       if (isUserNumberExists(user.getNumber())) {
           throw new ValidationException("A user with the same number already exists");
       }
       if (!isValidNumber(user.getNumber())) {
           throw new ValidationException("The number provided is not valid");
       }
       if (isExistsAccount(user.getAccount())) {
           throw new ValidationException("The account provided does not exist");
       }
    }

    private void validateUpdateUser(int id, UserRequestDTO user) {
        if (isUserNumberExistsById(user.getNumber(), id)) {
            throw new ValidationException("A user with the same number already exists");
        }
        if (!isValidNumber(user.getNumber())) {
            throw new ValidationException("The number provided is not valid");
        }
        if (isExistsAccountById(user.getAccount(), id)) {
            throw new ValidationException("The account provided does not exist");
        }

    }
    //create
    private boolean isUserNumberExists(String number) {
        return userRepository.existsByNumber(number);
    }

    private boolean isValidNumber(String number) {
        return number != null && number.length() == 9 && number.matches("\\d{9}");
    }

    private boolean isExistsAccount(int account_id) {
        return userRepository.existsByAccount_Id(account_id);
    }

    //update
    private boolean isExistsAccountById(int account_id, int id) {
        return userRepository.existsByAccount_IdAndIdNot(account_id, id);
    }
    private boolean isUserNumberExistsById(String number, int id) {
        return userRepository.existsByNumberAndIdNot(number, id);
    }



}
