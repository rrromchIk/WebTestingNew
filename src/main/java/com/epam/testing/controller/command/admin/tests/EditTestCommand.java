package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.entity.test.TestDifficulty;
import com.epam.testing.model.service.TestsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditTestCommand.class);
    private final TestsService testsService = new TestsService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("EditTestCommand execution started");
        String page = req.getContextPath() + Path.COMMAND_TEST_INFO;
        boolean redirect = true;

        long testId = Long.parseLong(req.getParameter("testId"));

        String subject = req.getParameter("subject");
        TestDifficulty difficulty = TestDifficulty.getEnum(Integer.parseInt(req.getParameter("difficulty")));
        int duration = Integer.parseInt(req.getParameter("duration"));
        int numOfQuestions = Integer.parseInt(req.getParameter("numOfQuestions"));

        Test test = testsService.getTestById(testId);
        test.setSubject(subject);
        test.setDifficulty(difficulty);
        test.setDuration(duration);
        test.setNumberOfQuestions(numOfQuestions);

        if(testsService.updateTest(test)) {
            page += "&testId=" + test.getId();
        } else {
            String command = page;
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "Unable to update test";
            req.setAttribute("commandToGoBack", command);
            req.setAttribute("errorMessage", errorMessage);
            redirect = false;

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("EditTestCommand execution finished");
        return new DispatchInfo(redirect, page);
    }
}
