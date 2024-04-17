package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.NotificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.NotificationResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.NotificationService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification", description = "Notification API")
@RestController
@RequestMapping("/api/v1/servifix")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Get a notification by ID")
    @GetMapping("/notifications/{id}")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> getNotificationById(@PathVariable("id") int id) {
        ApiResponse<NotificationResponseDTO> response = notificationService.getNotificationById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "create a new notification")
    @PostMapping("/notifications")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> createNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        var res = notificationService.createNotification(notificationRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "delete a notification")
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable("id") int id) {
        var res = notificationService.deleteNotification(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
