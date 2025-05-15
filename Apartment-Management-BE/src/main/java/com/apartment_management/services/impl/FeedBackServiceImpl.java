/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.services.impl;

import com.apartment_management.pojo.Feedback;
import com.apartment_management.pojo.User;
import com.apartment_management.repositories.FeedBackRepository;
import com.apartment_management.repositories.UserRepository;
import com.apartment_management.services.FeedBackService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author thien
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Feedback> getAllFeedbacks() {
        return this.feedBackRepo.getAllFeedbacks();
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(int userId) {
        User u = this.userRepo.getUserById(userId);
        return this.feedBackRepo.getFeedbacksByUserId(u);
    }

    @Override
    public Feedback createFeedback(int userId, String content) {
        User u = this.userRepo.getUserById(userId);
        Feedback feedback = new Feedback();
        feedback.setUserId(u);
        feedback.setContent(content);
        feedback.setCreatedAt(new Date());
        feedback.setStatus("PENDING");
        return this.feedBackRepo.createFeedback(feedback);
    }

    @Override
    public Feedback updateFeedback(int feedbackId, String content) {
        Feedback feedback = this.feedBackRepo.getFeedBackById(feedbackId);
        if (feedback != null) {
            feedback.setContent(content);
            return this.feedBackRepo.updateFeedbackStatus(feedback);
        }
        return null;
    }

    @Override
    public Feedback updateFeedbackStatus(int feedbackId, String status) {
        Feedback feedback = this.feedBackRepo.getFeedBackById(feedbackId);
        if (feedback != null) {
            feedback.setStatus(status);
            return this.feedBackRepo.updateFeedbackStatus(feedback);
        }
        return null;
    }

    @Override
    public void deleteFeedback(int feedbackId) {
        Feedback f = this.feedBackRepo.getFeedBackById(feedbackId);
        this.feedBackRepo.deleteFeedback(f);
    }

    @Override
    public Feedback getFeedBackById(int feedbackId) {
        return this.feedBackRepo.getFeedBackById(feedbackId);
    }
}
