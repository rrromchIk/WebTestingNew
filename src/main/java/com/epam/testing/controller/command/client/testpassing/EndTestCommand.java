package com.epam.testing.controller.command.client.testpassing;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.TestStatus;
import com.epam.testing.model.service.UserTestService;
import com.epam.testing.util.CalculateTestResultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EndTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EndTestCommand.class);
    private final UserTestService userTestService = new UserTestService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("EndTestCommand execution started");
        String page = Path.COMMAND_USER_MAIN + "&tab=passedTests";
        HttpSession httpSession = req.getSession();

        long userId = (long)httpSession.getAttribute("userId");
        long testId = Long.parseLong(req.getParameter("testId"));

        TestStatus testStatus = userTestService.getUserTestStatus(userId, testId);
        if(!testStatus.equals(TestStatus.PASSED)) {
            float testResult = CalculateTestResultService.getTestResult(testId, userId);
            userTestService.addResultAndEndingTime(userId, testId, testResult, Timestamp.valueOf(LocalDateTime.now()));
            userTestService.updateUserTestStatus(userId, testId, TestStatus.PASSED);
        }

        LOGGER.debug("EndTestCommand execution finished");
        return new DispatchInfo(false, page);
    }
}
