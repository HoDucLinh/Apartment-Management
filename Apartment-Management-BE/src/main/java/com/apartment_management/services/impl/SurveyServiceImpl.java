/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.services.impl;

import com.apartment_management.pojo.Survey;
import com.apartment_management.pojo.User;
import com.apartment_management.repositories.SurveyRepository;
import com.apartment_management.repositories.UserRepository;
import com.apartment_management.services.SurveyService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author thien
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Survey> getAllSurveys() {
        return surveyRepository.getAllSurveys();
    }

    @Override
    public Survey getSurveyById(int id) {
        return surveyRepository.getSurveyById(id);
    }

    @Override
    public void createSurvey(String title, String description) {
        Survey survey = new Survey();
        survey.setTitle(title);
        survey.setDescription(description);
        survey.setCreatedAt(new Date());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            User admin = userRepository.getUserByUserName(username);
            survey.setAdminId(admin);
        } else {
            throw new IllegalStateException("User not authenticated or invalid principal");
        }

        surveyRepository.createSurvey(survey);
    }

    @Override
    public void updateSurvey(Survey survey) {
        if (surveyRepository.getSurveyById(survey.getId()) != null) {
            surveyRepository.updateSurvey(survey);
        }
    }

    @Override
    public void deleteSurvey(int id) {
        if (surveyRepository.getSurveyById(id) != null) {
            surveyRepository.deleteSurvey(id);
        }
    }
}
