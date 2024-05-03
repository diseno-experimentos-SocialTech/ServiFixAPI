package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Account;
import org.springframework.data.repository.CrudRepository;

import javax.net.ssl.SSLSession;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Account getAccountById (int id);

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);


    boolean existsByEmailAndIdNot(String email, int id);
}
