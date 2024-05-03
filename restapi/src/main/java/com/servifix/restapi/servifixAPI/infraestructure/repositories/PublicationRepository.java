package com.servifix.restapi.servifixAPI.infraestructure.repositories;

import com.servifix.restapi.servifixAPI.domain.entities.Publication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Integer> {

    List<Publication> getPublicationByUserId(Integer userId);

    Publication getPublicationById(Integer id);

    boolean existsByTitleAndDescriptionAndAddress(String title, String description, String address);

    boolean existsByTitleAndDescriptionAndAddressAndIdNot(String title, String description, String address, int id);
}
