package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, Integer> {

    Offer getOfferById (int id);

    List<Offer> getOfferByTechnicalId(int technicalId);

    List<Offer> getOfferByPublicationId(int publicationId);

    List<Offer> getOfferByPublicationIdAndStateOfferId(int publicationId, int stateId);

}
