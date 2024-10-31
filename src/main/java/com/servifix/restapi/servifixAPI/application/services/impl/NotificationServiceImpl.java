package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.NotificationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.NotificationResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.NotificationService;
import com.servifix.restapi.servifixAPI.domain.entities.Notification;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.AccountRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.NotificationRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper modelMapper, AccountRepository accountRepository) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public ApiResponse<NotificationResponseDTO> getNotificationById(int id){
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            NotificationResponseDTO responseDTO = modelMapper.map(notification, NotificationResponseDTO.class);
            return new ApiResponse<>("Notification fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Notification not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<NotificationResponseDTO> createNotification(NotificationRequestDTO notificationRequestDTO){
        var notification = modelMapper.map(notificationRequestDTO, Notification.class);
        notification.setAccount(accountRepository.getAccountById(notificationRequestDTO.getAccount()));
        notificationRepository.save(notification);

        var response = modelMapper.map(notification, NotificationResponseDTO.class);

        return new ApiResponse<>("Notification created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<Void> deleteNotification(int id){
        notificationRepository.deleteById(id);
        return new ApiResponse<>("Notification deleted successfully", Estatus.SUCCESS, null);
    }

}
