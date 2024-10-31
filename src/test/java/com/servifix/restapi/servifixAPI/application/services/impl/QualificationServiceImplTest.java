package com.servifix.restapi.servifixAPI.application.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
class QualificationServiceImplTest {

    private QualificationServiceImpl qualificationService;
    @Test
    public void testIsValidQualification(){
        qualificationService = new QualificationServiceImpl();
        float quality = 6.0f;
        assertFalse(qualificationService.isValidQualification(quality));
    }

    @ParameterizedTest
    @ValueSource(floats = { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f })
    public void testIsValidQualification(float quality){
        qualificationService = new QualificationServiceImpl();
        assertTrue(qualificationService.isValidQualification(quality));
    }

    @ParameterizedTest
    @ValueSource(floats = { 8.0f, 7.0f, 0.0f })
    public void testIsValidQualificationFalse(float quality){
        qualificationService = new QualificationServiceImpl();
        assertFalse(qualificationService.isValidQualification(quality));
    }
}