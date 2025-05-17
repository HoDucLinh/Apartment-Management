package com.apartment_management.controllers;

import com.apartment_management.pojo.Invoice;
import com.apartment_management.services.InvoiceService;
import com.apartment_management.services.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
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

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<?> createPayment(HttpServletRequest request,  @RequestBody Map<String, Object> body) {
        int amount = ((Number) body.get("amount")).intValue();  
        String returnUrl = "http://localhost:8080";
        String response = vnpayService.createOrder(request, amount, "Thanh toan don hang", returnUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/pay-invoice/{invoiceId}")
    public ResponseEntity<?> payInvoice(@PathVariable int invoiceId, HttpServletRequest request) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice == null || invoice.getStatus() == "PENDING") {
            return ResponseEntity.badRequest().body("Hóa đơn không hợp lệ hoặc đã thanh toán");
        }
        int amount = invoice.getTotalAmount().intValue();
        String returnUrl = "http://localhost:8080";

        String response = vnpayService.createOrder(request, amount, "Thanh toan hoa don " + invoiceId, returnUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay-return")
    public void returnPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int result = vnpayService.orderReturn(request);

        // Tùy theo trạng thái, gắn query param để frontend biết kết quả
        String redirectUrl = "http://localhost:3000/apartment-management/payment/vnpay-payment-return";

        if (result == 1) {
            redirectUrl += "?status=success";
        } else if (result == 0) {
            redirectUrl += "?status=fail";
        } else {
            redirectUrl += "?status=invalid";
        }

        response.sendRedirect(redirectUrl);
    }

}
