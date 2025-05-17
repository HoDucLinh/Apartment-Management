/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.services.impl;

import com.apartment_management.pojo.Invoice;
import com.apartment_management.repositories.InvoiceRepository;
import com.apartment_management.services.InvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getInvoicesByUserId(int userId) {
        return invoiceRepository.getInvoicesByUserId(userId);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        return invoiceRepository.getInvoiceById(id);
    }

}
