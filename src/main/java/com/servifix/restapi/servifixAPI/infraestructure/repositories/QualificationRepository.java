package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Qualification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QualificationRepository extends CrudRepository<Qualification, Integer> {

    Qualification getQualificationById(int id);

    List<Qualification> getQualificationByReviewId(int reviewId);

    List<Qualification> getQualificationByOfferId(int offerId);

}
