/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.apartment_management.services;

import com.apartment_management.pojo.Survey;
import java.util.List;

/**
 *
 * @author thien
 */
public interface SurveyService {

    List<Survey> getAllSurveys();

    Survey getSurveyById(int id);

    void createSurvey(String title, String description);

    void updateSurvey(Survey survey);

    void deleteSurvey(int id);
}
