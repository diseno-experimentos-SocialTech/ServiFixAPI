package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.AccountRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.request.PublicationRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.AccountResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PublicationResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.TechnicalResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.PublicationService;
import com.servifix.restapi.servifixAPI.domain.entities.Account;
import com.servifix.restapi.servifixAPI.domain.entities.Publication;
import com.servifix.restapi.servifixAPI.domain.entities.Technical;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.JobRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.PublicationRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.UserRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.hibernate.sql.ast.tree.expression.Over;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public PublicationServiceImpl(PublicationRepository publicationRepository, ModelMapper modelMapper, UserRepository userRepository, JobRepository jobRepository) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public ApiResponse<List<PublicationResponseDTO>> getPublications() {
        List<Publication> publicationList = (List<Publication>) publicationRepository.findAll();

        List<PublicationResponseDTO> publicationResponseDTOList = publicationList.stream()
                .map(entity -> modelMapper.map(entity, PublicationResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All publications fetched successfully", Estatus.SUCCESS, publicationResponseDTOList);
    }

    @Override
    public ApiResponse<PublicationResponseDTO> getPublicationById(int id) {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);

        if (publicationOptional.isPresent()) {
            Publication publication = publicationOptional.get();
            PublicationResponseDTO responseDTO = modelMapper.map(publication, PublicationResponseDTO.class);
            return new ApiResponse<>("Publication fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Publication not found", Estatus.ERROR, null);
        }
    }
    @Override
    public ApiResponse<PublicationResponseDTO> createPublication(PublicationRequestDTO publicationRequestDTO) {
        var publication = modelMapper.map(publicationRequestDTO, Publication.class);
        ValidatePublication(publicationRequestDTO);
        publication.setUser(userRepository.getUserById(publicationRequestDTO.getUser()));
        publication.setJob(jobRepository.getJobById(publicationRequestDTO.getJob()));
        publicationRepository.save(publication);

        var response = modelMapper.map(publication, PublicationResponseDTO.class);

        return new ApiResponse<>("Publication created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<PublicationResponseDTO> updatePublication(int id, PublicationRequestDTO publicationRequestDTO) {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);

        if (publicationOptional.isEmpty()) {
            return new ApiResponse<>("Publication not found", Estatus.ERROR, null);
        }else {
            Publication publication = publicationOptional.get();
            modelMapper.map(publicationRequestDTO, publication);
            ValidateUpdatePublication(id, publicationRequestDTO);
            publication.setUser(userRepository.getUserById(publicationRequestDTO.getUser()));
            publication.setJob(jobRepository.getJobById(publicationRequestDTO.getJob()));
            publicationRepository.save(publication);
            PublicationResponseDTO response = modelMapper.map(publication, PublicationResponseDTO.class);
            return new ApiResponse<>("Publication updated successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<Void> deletePublication(int id) {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);

        if (publicationOptional.isPresent()) {
            publicationRepository.deleteById(id);
            return new ApiResponse<>("Publication deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Publication not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<List<PublicationResponseDTO>> getPublicationByUserId(int userId){
        List<Publication> publicationList = publicationRepository.getPublicationByUserId(userId);

        List<PublicationResponseDTO> publicationResponseDTOList = publicationList.stream()
                .map(entity -> modelMapper.map(entity, PublicationResponseDTO.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("All publications fetched successfully", Estatus.SUCCESS, publicationResponseDTOList);
    }

    private void ValidatePublication(PublicationRequestDTO publicationRequestDTO) {
        if (!isValidateAmount(publicationRequestDTO.getAmount())) {
            throw new ValidationException("Amount must be greater than 0");
        }
        if (isExistsPublicationByTitleByDescriptionByAddress(publicationRequestDTO.getTitle(), publicationRequestDTO.getDescription(), publicationRequestDTO.getAddress())) {
            throw new ValidationException("There is already a publication with the same title, description and address");
        }
    }
    private void ValidateUpdatePublication(int id, PublicationRequestDTO publicationRequestDTO) {
        if (!isValidateAmount(publicationRequestDTO.getAmount())) {
            throw new ValidationException("Amount must be greater than 0");
        }
        if (isExistsPublicationByTitleByDescriptionByAddressById(publicationRequestDTO.getTitle(), publicationRequestDTO.getDescription(), publicationRequestDTO.getAddress(), id)) {
            throw new ValidationException("There is already a publication with the same title, description and address");
        }
    }

    private boolean isValidateAmount(double amount) {
        return amount > 0;
    }

    private boolean isExistsPublicationByTitleByDescriptionByAddress(String title, String description, String address) {
        return publicationRepository.existsByTitleAndDescriptionAndAddress(title, description, address);
    }
    private boolean isExistsPublicationByTitleByDescriptionByAddressById(String title, String description, String address, int id) {
        return publicationRepository.existsByTitleAndDescriptionAndAddressAndIdNot(title, description, address, id);
    }
}
