package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import org.springframework.data.repository.CrudRepository;

public interface TechnicalRepository extends CrudRepository<Technical, Integer> {

    Technical getTechnicalById(int id);

}
