package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.service.TestQuestionService;
import com.epam.testing.model.service.TestsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddQuestionsCommand.class);
    private final TestsService testsService = new TestsService();
    private final TestQuestionService testQuestionService = new TestQuestionService();
    private static final int MAX_AMOUNT_OF_ANSWER_VARIANTS = 10;

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("AddQuestionsCommand execution started");
        String page = Path.PAGE_ADD_QUESTIONS;

        long testId = Long.parseLong(req.getParameter("testId"));
        Test test = testsService.getTestById(testId);

        int amountOfAddedQuestions = testQuestionService.getAmountOfAddedQuestions(test.getId());
        int amountOfQuestionsInTheTest = test.getNumberOfQuestions();

        if(amountOfAddedQuestions >= amountOfQuestionsInTheTest) {
            page = Path.COMMAND_ADMIN_MAIN;
        } else {
            req.setAttribute("maxAmountOfAnswers", MAX_AMOUNT_OF_ANSWER_VARIANTS);
            req.setAttribute("questionNumber", amountOfAddedQuestions + 1);
            req.setAttribute("fullTest", test);
            req.setAttribute("testId", testId);
        }

        LOGGER.debug("AddQuestionsCommand execution finished");
        return new DispatchInfo(false, page);
    }
}
