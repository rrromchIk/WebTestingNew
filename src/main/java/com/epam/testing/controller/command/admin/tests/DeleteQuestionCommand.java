package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.service.TestQuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteQuestionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteQuestionCommand.class);
    private final TestQuestionService testQuestionService = new TestQuestionService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("DeleteQuestionCommand execution started");
        long questionId = Long.parseLong(req.getParameter("questionId"));
        long testId = Long.parseLong(req.getParameter("testId"));

        String command = req.getContextPath() + Path.COMMAND_TEST_INFO;
        command += "&testId=" + testId;

        testQuestionService.deleteQuestion(questionId);

        LOGGER.debug("DeleteQuestionCommand execution finished");
        return new DispatchInfo(true, command);
    }
}
