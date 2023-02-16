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

public class SubmitTestInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SubmitTestInfoCommand.class);
    private final TestsService testsService = new TestsService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("SubmitTestInfoCommand execution started");
        String page = req.getContextPath() + Path.COMMAND_ADD_QUESTIONS;
        boolean redirect = true;

        Test test = new Test.TestBuilder()
                .name(req.getParameter("name"))
                .subject(req.getParameter("subject"))
                .difficulty(TestDifficulty.getEnum(Integer.parseInt(req.getParameter("difficulty"))))
                .duration(Integer.parseInt(req.getParameter("duration")))
                .numberOfQuestions(Integer.parseInt(req.getParameter("numOfQuestions")))
                .build();

        if(testsService.createTest(test)) {
            page += "&testId=" + test.getId();
        } else {
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "Unable to create test";
            req.setAttribute("commandToGoBack", Path.COMMAND_ADD_TEST);
            req.setAttribute("errorMessage", errorMessage);
            redirect = false;

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("SubmitTestInfoCommand execution finished");
        return new DispatchInfo(redirect, page);
    }
}
