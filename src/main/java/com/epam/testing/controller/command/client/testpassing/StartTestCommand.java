package com.epam.testing.controller.command.client.testpassing;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.TestStatus;
import com.epam.testing.model.service.UserTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StartTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(StartTestCommand.class);
    private final UserTestService userTestService = new UserTestService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("StartTestCommand execution started");
        String page = req.getContextPath() + Path.COMMAND_USER_PASS_TEST;
        boolean redirect = true;

        HttpSession httpSession = req.getSession();

        long userId = (long)httpSession.getAttribute("userId");
        long testId = Long.parseLong(req.getParameter("testId"));

        if(userTestService.addTestToUsersTests(userId, testId, Timestamp.valueOf(LocalDateTime.now()))) {
            userTestService.updateUserTestStatus(userId, testId, TestStatus.STARTED);
        } else {
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "Failed to start test. Try again later!";
            req.setAttribute("commandToGoBack", Path.COMMAND_USER_MAIN);
            req.setAttribute("errorMessage", errorMessage);
            redirect = false;

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("StartTestCommand execution finished");
        page += "&testId=" + testId;
        return new DispatchInfo(redirect, page);
    }
}
