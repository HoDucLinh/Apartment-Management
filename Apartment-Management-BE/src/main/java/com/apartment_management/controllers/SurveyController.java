/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.controllers;

import com.apartment_management.pojo.Question;
import com.apartment_management.pojo.Response;
import com.apartment_management.pojo.Survey;
import com.apartment_management.services.QuestionService;
import com.apartment_management.services.ResponseService;
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

    @Autowired
    private ResponseService responseService;

    @GetMapping
    public String listSurveys(Model model) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("title", "Quản lý khảo sát");
        return "survey_list";
    }

    @GetMapping("/detail/{id}")
    public String surveyDetail(@PathVariable("id") int id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            return "redirect:/survey-manage";
        }

        List<Question> questions = questionService.getQuestionsBySurveyId(id);

        model.addAttribute("survey", survey);
        model.addAttribute("questions", questions);
        model.addAttribute("newQuestion", new Question());
        return "survey_detail";
    }

    @PostMapping("/{surveyId}/question/add")
    public String addQuestion(@PathVariable("surveyId") int surveyId,
            @RequestParam("content") String content) {
        questionService.addQuestion(content, surveyId);
        return "redirect:/survey-manage/detail/" + surveyId;
    }

    @PostMapping("/{surveyId}/question/edit/{questionId}")
    public String editQuestion(@PathVariable("surveyId") int surveyId,
            @PathVariable("questionId") int questionId,
            @RequestParam("content") String content) {
        questionService.updateQuestion(questionId, content);
        return "redirect:/survey-manage/detail/" + surveyId;
    }

    @PostMapping("/{surveyId}/question/delete/{questionId}")
    public String deleteQuestion(@PathVariable("surveyId") int surveyId,
            @PathVariable("questionId") int questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/survey-manage/detail/" + surveyId;
    }

    @PostMapping("/create")
    public String createSurvey(@ModelAttribute Survey survey) {
        surveyService.createSurvey(survey.getTitle(), survey.getDescription());
        return "redirect:/survey-manage";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            return "redirect:/survey-manage";
        }

        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("editingSurveyId", id);
        model.addAttribute("surveyToEdit", survey);
        return "survey_list";
    }

    @PostMapping("/edit/{id}")
    public String editSurvey(@PathVariable("id") int id,
            @ModelAttribute Survey survey) {
        survey.setId(id);
        surveyService.updateSurvey(survey);
        return "redirect:/survey-manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable("id") int id) {
        surveyService.deleteSurvey(id);
        return "redirect:/survey-manage";
    }

    @GetMapping("/detail/{surveyId}/{questionId}/answers")
    public String viewQuestionAnswers(
            @PathVariable("surveyId") int surveyId,
            @PathVariable("questionId") int questionId,
            Model model) {

        Survey survey = surveyService.getSurveyById(surveyId);
        Question question = questionService.getQuestionById(questionId);

        if (survey == null || question == null) {
            return "redirect:/survey-manage";
        }

        List<Response> answers = responseService.findByQuestionId(questionId);

        System.out.println("Dữ liệu questions: " + answers);

        model.addAttribute("survey", survey);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "survey_question_answers";
    }

}
