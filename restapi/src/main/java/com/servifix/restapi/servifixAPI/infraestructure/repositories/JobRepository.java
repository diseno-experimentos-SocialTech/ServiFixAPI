package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Integer> {

    Job getJobById(int id);

    boolean existsJobByName(String name);

    boolean existsJobByNameAndIdNot(String name, int id);
}
