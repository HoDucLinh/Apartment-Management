/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.controllers;

import com.apartment_management.pojo.Question;
import com.apartment_management.pojo.Survey;
import com.apartment_management.services.QuestionService;
import com.apartment_management.services.SurveyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author thien
 */
@Controller
@RequestMapping("/survey-manage")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    // 1. Hiển thị danh sách khảo sát
    @GetMapping
    public String listSurveys(Model model) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("title", "Quản lý khảo sát");
        return "survey_list"; // View: hiển thị danh sách
    }

    // 2. Xem chi tiết khảo sát + danh sách câu hỏi
    @GetMapping("/detail/{id}")
    public String surveyDetail(@PathVariable("id") int id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null)
            return "redirect:/survey-manage";

        List<Question> questions = questionService.getQuestionsBySurveyId(id);

        model.addAttribute("survey", survey);
        model.addAttribute("questions", questions);
        model.addAttribute("newQuestion", new Question());
        return "survey_detail"; // View: chi tiết + danh sách câu hỏi
    }

    // 3. Thêm câu hỏi mới
@PostMapping("/{surveyId}/question/add")
public String addQuestion(@PathVariable("surveyId") int surveyId,
                          @RequestParam("content") String content) {
    questionService.addQuestion(content, surveyId);
    return "redirect:/survey-manage/detail/" + surveyId;
}



    // 4. Sửa nội dung câu hỏi
    @PostMapping("/{surveyId}/question/edit/{questionId}")
    public String editQuestion(@PathVariable("surveyId") int surveyId,
                               @PathVariable("questionId") int questionId,
                               @ModelAttribute Question question) {
        questionService.updateQuestion(questionId, question.getContent());
        return "redirect:/survey-manage/detail/" + surveyId;
    }

    // 5. Xoá câu hỏi
    @PostMapping("/{surveyId}/question/delete/{questionId}")
    public String deleteQuestion(@PathVariable("surveyId") int surveyId,
                                 @PathVariable("questionId") int questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/survey-manage/detail/" + surveyId;
    }

    // 6. Hiển thị form tạo khảo sát
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("survey", new Survey());
        model.addAttribute("formTitle", "Tạo khảo sát mới");
        model.addAttribute("formAction", "/survey-manage/create");
        return "survey_form"; // => tách riêng file survey_form.html
    }

    // 7. Xử lý tạo khảo sát
    @PostMapping("/create")
    public String createSurvey(@ModelAttribute Survey survey) {
        surveyService.createSurvey(survey.getTitle(), survey.getDescription());
        return "redirect:/survey-manage";
    }

    // 8. Hiển thị form sửa khảo sát
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null)
            return "redirect:/survey-manage";

        model.addAttribute("survey", survey);
        model.addAttribute("formTitle", "Chỉnh sửa khảo sát");
        model.addAttribute("formAction", "/survey-manage/edit/" + id);
        return "survey_form"; 
    }

    // 9. Xử lý cập nhật khảo sát
    @PostMapping("/edit/{id}")
    public String editSurvey(@PathVariable("id") int id,
                             @ModelAttribute Survey survey) {
        survey.setId(id);
        surveyService.updateSurvey(survey);
        return "redirect:/survey-manage";
    }

    // 10. Xử lý xoá khảo sát
    @PostMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable("id") int id) {
        surveyService.deleteSurvey(id);
        return "redirect:/survey-manage";
    }
}