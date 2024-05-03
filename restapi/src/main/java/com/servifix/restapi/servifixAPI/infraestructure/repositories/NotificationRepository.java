package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.domain.entities.Notification;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {


    boolean existsByTitleAndContent(String title, String content);

    boolean existsByAccount_Id(int accountId);

}
