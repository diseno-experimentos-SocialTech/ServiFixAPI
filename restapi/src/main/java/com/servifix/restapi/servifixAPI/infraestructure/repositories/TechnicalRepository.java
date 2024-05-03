package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TechnicalRepository extends CrudRepository<Technical, Integer> {

    Technical getTechnicalById(int id);

    boolean existsByNumber(String number);

    boolean existsByPoliceRecords(String policeRecords);

    boolean existsByAccount_Id(int accountId);

}
