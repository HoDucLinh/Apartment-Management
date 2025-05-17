/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.apartment_management.services;

import com.apartment_management.pojo.Invoice;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface InvoiceService {
    List<Invoice> getInvoicesByUserId(int userId);
    Invoice getInvoiceById(int id);
}
