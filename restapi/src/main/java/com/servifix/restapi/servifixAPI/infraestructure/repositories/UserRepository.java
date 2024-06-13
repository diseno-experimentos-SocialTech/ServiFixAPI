package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    boolean existsById(int id);

    User getUserById(int id);

    boolean existsByNumber(String number);

    boolean existsByAccount_Id(int account_id);

    boolean existsByAccount_IdAndIdNot(int account_id, int id);

    boolean existsByNumberAndIdNot(String number, int id);

    User getUserByAccount_Id(int account_id);
}
