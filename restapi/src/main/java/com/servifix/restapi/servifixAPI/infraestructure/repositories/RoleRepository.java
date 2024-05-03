package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role getRoleById (int id);

    boolean existsById(int id);

    boolean existsByType(String type);

    boolean existsByTypeAndIdNot(String type, int id);
}
