package com.servifix.restapi.servifixAPI.application.controller;

import com.servifix.restapi.servifixAPI.application.dto.request.PaymentRequestDTO;
import com.servifix.restapi.servifixAPI.application.dto.response.PaymentResponseDTO;
import com.servifix.restapi.servifixAPI.application.services.PaymentService;
import com.servifix.restapi.shared.model.dto.response.ApiResponse;
import com.servifix.restapi.shared.model.enums.Estatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "Payment API")
@RestController
@RequestMapping("/api/v1/servifix")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get payment by ID")
    @GetMapping("/payments/{id}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> getPaymentById(@PathVariable("id") int id) {
        ApiResponse<PaymentResponseDTO> response = paymentService.getPaymentById(id);
        return new ResponseEntity<>(response, response.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create a new payment")
    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        var res = paymentService.createPayment(paymentRequestDTO);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a payment")
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable("id") int id) {
        var res = paymentService.deletePayment(id);
        return new ResponseEntity<>(res, res.getStatus() == Estatus.SUCCESS ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
