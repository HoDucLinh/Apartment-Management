/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.apartment_management.services;

import com.apartment_management.pojo.Feedback;
import java.util.List;

/**
 *
 * @author thien
 */
public interface FeedBackService {

    List<Feedback> getAllFeedbacks();

    List<Feedback> getFeedbacksByUserId(int userId);

    Feedback getFeedBackById(int feedbackId);

    Feedback createFeedback(int userId, String content);

    Feedback updateFeedback(int feedbackId, String content);

    Feedback updateFeedbackStatus(int feedbackId, String status);

    void deleteFeedback(int feedbackId);
}
