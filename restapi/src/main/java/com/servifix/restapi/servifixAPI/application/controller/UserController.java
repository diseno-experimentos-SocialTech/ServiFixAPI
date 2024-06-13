package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.UserRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.UserService;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/v1/servifix")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "get a user by id")
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable("id") int id) {
        ApiResponse<UserResponseDTO> response = userService.getUserById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "get a user by account id")
    @GetMapping("/users/account/{account_id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByAccount(@PathVariable("account_id") int account_id) {
        ApiResponse<UserResponseDTO> response = userService.getUserByAccount(account_id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "get all users")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        var res = userService.getUsers();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "create a new user")
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        var res = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @Operation(summary = "update an existing user")
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable int id, @RequestBody UserRequestDTO userRequestDTO) {
        var res = userService.updateUser(id, userRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "delete a user")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int id) {
        var res = userService.deleteUser(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
