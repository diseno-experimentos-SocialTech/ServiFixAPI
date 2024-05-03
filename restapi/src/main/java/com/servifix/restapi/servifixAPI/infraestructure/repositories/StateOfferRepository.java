package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.StateOffer;
import org.springframework.data.repository.CrudRepository;

public interface StateOfferRepository extends CrudRepository<StateOffer, Integer> {

    StateOffer getStateOfferById(int id);

    boolean existsByState(String state);
}
