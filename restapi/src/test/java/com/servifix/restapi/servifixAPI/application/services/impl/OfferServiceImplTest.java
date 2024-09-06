package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.OfferRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.OfferResponseDTO;
import com.servifix.restapi.servifixAPI.domain.entities.Offer;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OfferServiceImpl offerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOfferById_offerExists_returnOffer() {
        // Instanciamos los dats de prueba
        int offerId = 1;
        Offer offer = new Offer();
        OfferResponseDTO offerResponseDTO = new OfferResponseDTO();

        // Configurar los mocks
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(modelMapper.map(offer, OfferResponseDTO.class)).thenReturn(offerResponseDTO);

        // Ejecutar el método
        ApiResponse<OfferResponseDTO> response = offerService.getOfferById(offerId);

        // Verificar el resultado
        assertEquals(Estatus.SUCCESS, response.getStatus());
        assertEquals("Offer fetched successfully", response.getMessage());
        assertNotNull(response.getData());

        // Verificar que los mocks fueron llamados
        verify(offerRepository).findById(offerId);
        verify(modelMapper).map(offer, OfferResponseDTO.class);
    }

    @Test
    // mensaje de error
    void getOfferById_offerDoesNotExist_returnError() {
        // Datos de prueba
        int offerId = 1;

        // Configurar los mocks
        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // Ejecutar el método
        ApiResponse<OfferResponseDTO> response = offerService.getOfferById(offerId);

        // Verificar el resultado
        assertEquals(Estatus.ERROR, response.getStatus());
        assertEquals("Offer not found", response.getMessage());
        assertNull(response.getData());

        // Verificar que los mocks fueron llamados
        verify(offerRepository).findById(offerId);
    }

}
