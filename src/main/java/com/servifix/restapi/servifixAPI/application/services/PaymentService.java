package com.servifix.restapi.servifixAPI.application.services;

import com.servifix.restapi.servifixAPI.application.dto.request.PaymentRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PaymentResponseDTO;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;

public interface PaymentService {

    ApiResponse<PaymentResponseDTO> createPayment(PaymentRequestDTO paymentRequestDTO);

    ApiResponse<PaymentResponseDTO> getPaymentById(int paymentId);

    ApiResponse<Void> deletePayment(int paymentId);

}
