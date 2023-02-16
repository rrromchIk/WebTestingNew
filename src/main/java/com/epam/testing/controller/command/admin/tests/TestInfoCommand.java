package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.service.TestQuestionService;
import com.epam.testing.model.service.TestsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TestInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TestInfoCommand.class);
    private final TestsService testsService = new TestsService();
    private final TestQuestionService testQuestionService = new TestQuestionService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("TestInfoCommand execution started");
        String page;
        long testId = Long.parseLong(req.getParameter("testId"));
        Test test = testsService.getTestById(testId);

        if(test != null) {
            List<Question> questions = testQuestionService.getQuestionsByTestId(testId);

            int amountOfQuestions = test.getNumberOfQuestions();
            int actualAdded = testQuestionService.getAmountOfAddedQuestions(testId);
            if(actualAdded < amountOfQuestions) {
                req.setAttribute("addQuestions", true);
            }
            req.setAttribute("questions", questions);
            req.setAttribute("fullTest", test);
            page = Path.PAGE_TEST_INFO;
        } else {
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "Test not found";
            req.setAttribute("commandToGoBack", Path.COMMAND_ADMIN_MAIN);
            req.setAttribute("errorMessage", errorMessage);

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("TestInfoCommand execution finished");
        return new DispatchInfo(false, page);
    }
}
