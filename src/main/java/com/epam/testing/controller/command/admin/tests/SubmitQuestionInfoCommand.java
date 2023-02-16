package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.question.QuestionType;
import com.epam.testing.model.service.TestQuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SubmitQuestionInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SubmitQuestionInfoCommand.class);
    private final TestQuestionService testQuestionService = new TestQuestionService();
    private static final int MAX_AMOUNT_OF_ANSWER_VARIANTS = 10;

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("SubmitQuestionInfoCommand execution started");
        String command = req.getContextPath() + Path.COMMAND_ADD_QUESTIONS;

        long testId = Long.parseLong(req.getParameter("testId"));
        String questionText = req.getParameter("questionText");
        int maxScore = Integer.parseInt(req.getParameter("maxScore"));
        List<String> answers = new ArrayList<>();
        List<String> correctAnswers = new ArrayList<>();
        QuestionType questionType = setQuestionInfo(req, answers, correctAnswers);

        Question question = new Question.QuestionBuilder()
                .text(questionText)
                .maxScore(maxScore)
                .type(questionType)
                .build();

        testQuestionService.addQuestionToTheTest(testId, question);
        answers.forEach(answer -> testQuestionService.addQuestionAnswer(question.getId(), answer));
        correctAnswers.forEach(correctAnswer -> testQuestionService.addCorrectAnswer(question.getId(), correctAnswer));

        LOGGER.debug("SubmitQuestionInfoCommand execution finished");
        command += "&testId=" + testId;
        return new DispatchInfo(true, command);
    }

    private QuestionType setQuestionInfo(HttpServletRequest request,
                                         List<String> answers,
                                         List<String> correctAnswers) {
        int i = 0;
        while(i != MAX_AMOUNT_OF_ANSWER_VARIANTS) {
            String text = request.getParameter("answer" + i);
            String checkBox = request.getParameter("answer" + i + "correct");
            i++;

            if(text != null) {
                answers.add(text);
            }
            if(text != null && checkBox != null) {

                correctAnswers.add(text);
            }
        }

        return  correctAnswers.size() > 1
                ? QuestionType.MULTIPLE_CORRECT_ANSWERS
                : QuestionType.SINGLE_CORRECT_ANSWER;
    }
}
