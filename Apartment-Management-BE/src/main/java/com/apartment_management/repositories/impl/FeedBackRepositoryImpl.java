/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.repositories.impl;

import com.apartment_management.pojo.Feedback;
import com.apartment_management.pojo.User;
import com.apartment_management.repositories.FeedBackRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thien
 */
@Repository
@Transactional
public class FeedBackRepositoryImpl implements FeedBackRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return factory.getObject().getCurrentSession();
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        Query q = this.getCurrentSession().createQuery("FROM Feedback", Feedback.class);
        return q.getResultList();
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(User u) {
        Query q = this.getCurrentSession().createQuery("FROM Feedback f WHERE f.userId = :user", Feedback.class);
        q.setParameter("user", u);
        return q.getResultList();
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        this.getCurrentSession().persist(feedback);
        return feedback;
    }

    @Override
    public Feedback updateFeedbackStatus(Feedback feedback) {
        return this.getCurrentSession().merge(feedback);
    }

    @Override
    public Feedback getFeedBackById(int feedbackId) {
        return this.getCurrentSession().get(Feedback.class, feedbackId);
    }

    @Override
    public Feedback updateFeedback(Feedback feedback) {
        return this.getCurrentSession().merge(feedback);
    }

    @Override
    public void deleteFeedback(Feedback feedback) {
        this.getCurrentSession().remove(feedback);
    }

}
