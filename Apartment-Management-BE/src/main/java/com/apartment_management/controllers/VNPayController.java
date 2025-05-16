package com.apartment_management.controllers;

import com.apartment_management.services.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payment")
public class VNPayController {

    @Autowired
    private VNPayService vnpayService;

    @PostMapping
    public ResponseEntity<?> createPayment(HttpServletRequest request) {
        String returnUrl = "https://25c0-116-110-40-232.ngrok-free.app/apartment-management/api/payment/vnpay-return";
        String response = vnpayService.createOrder(request, 10000, "Thanh toan don hang", returnUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<String> returnPayment(HttpServletRequest request) {
        int result = vnpayService.orderReturn(request);
        if (result == 1) return ResponseEntity.ok("Thanh toán thành công");
        if (result == 0) return ResponseEntity.ok("Thanh toán thất bại");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai checksum");
    }
}
