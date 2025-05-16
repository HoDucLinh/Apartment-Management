/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.controllers;

import com.apartment_management.pojo.Feedback;
import com.apartment_management.pojo.User;
import com.apartment_management.services.FeedBackService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author thien
 */
@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedBackService feedbackSer;

    // Hiển thị danh sách feedback
    @GetMapping
    public String listFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackSer.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedback_list";
    }

    // Xem chi tiết một feedback
    @GetMapping("/{id}")
    public String feedbackDetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("feedback", feedbackSer.getFeedBackById(id));
        return "feedback_detail";
    }

    // Cập nhật trạng thái feedback
    @PostMapping("/{id}/status")
    public String updateFeedbackStatus(@PathVariable("id") int id,
            @RequestParam("status") String status) {
        feedbackSer.updateFeedbackStatus(id, status);
        return "redirect:/admin/feedbacks";
    }
}
