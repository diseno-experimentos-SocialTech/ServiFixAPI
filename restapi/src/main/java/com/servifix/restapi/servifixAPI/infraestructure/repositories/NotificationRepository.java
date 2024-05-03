package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.domain.entities.Notification;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {


    boolean existsByTitleAndContentAndAccount_Id(String title, String content, int account_id);



}
