package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

    Payment getPaymentsById(Integer id);

}
