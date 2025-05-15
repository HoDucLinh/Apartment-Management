/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.controllers;

import com.apartment_management.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author thien
 */
@Controller 
@RequestMapping
public class ResponseFBController {
    
    @Autowired
    private ResponseService responseSer; 
   
    public void createResponse(Integer questionId, String answer) {
      
    }
}
