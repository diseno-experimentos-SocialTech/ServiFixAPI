package com.servifix.restapi.servifixAPI.application.services.impl;

import com.servifix.restapi.servifixAPI.application.dto.request.PaymentRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PaymentResponseDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.UserResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.PaymentService;
import com.servifix.restapi.servifixAPI.domain.entities.Payment;
import com.servifix.restapi.servifixAPI.domain.entities.User;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.OfferRepository;
import com.servifix.restapi.servifixAPI.infraestructure.repositories.PaymentRepository;
import com.servifix.restapi.shared.exception.ValidationException;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import org.hibernate.sql.ast.tree.expression.Over;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper modelMapper, OfferRepository offerRepository) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
        this.offerRepository = offerRepository;
    }

    @Override
    public ApiResponse<PaymentResponseDTO> getPaymentById(int paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            PaymentResponseDTO responseDTO = modelMapper.map(payment, PaymentResponseDTO.class);
            return new ApiResponse<>("Payment fetched successfully", Estatus.SUCCESS, responseDTO);
        } else {
            return new ApiResponse<>("Payment not found", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<PaymentResponseDTO> createPayment(PaymentRequestDTO paymentRequestDTO) {
        var payment = modelMapper.map(paymentRequestDTO, Payment.class);
        validatePayment(paymentRequestDTO);
        payment.setOffer(offerRepository.getOfferById(paymentRequestDTO.getOffer()));
        paymentRepository.save(payment);

        var response = modelMapper.map(payment, PaymentResponseDTO.class);

        return new ApiResponse<>("Payment created successfully", Estatus.SUCCESS, response);
    }

    @Override
    public ApiResponse<Void> deletePayment(int paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isPresent()) {
            paymentRepository.deleteById(paymentId);
            return new ApiResponse<>("Payment deleted successfully", Estatus.SUCCESS, null);
        } else {
            return new ApiResponse<>("Payment not found", Estatus.ERROR, null);
        }
    }

    private void validatePayment(PaymentRequestDTO payment) {
        if (!isValidateAmount(payment.getAmount())) {
            throw new ValidationException("El monto debe ser mayor a 0");
        }
    }

    private boolean isValidateAmount(double amount) {
        return amount > 0;
    }
}
