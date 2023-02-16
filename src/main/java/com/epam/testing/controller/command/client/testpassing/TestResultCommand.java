package com.epam.testing.controller.command.client.testpassing;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.TestStatus;
import com.epam.testing.model.service.UserTestService;
import com.epam.testing.util.PdfBuilderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestResultCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TestResultCommand.class);
    private final UserTestService userTestService = new UserTestService();
    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("TestResultCommand execution started");
        String command = Path.COMMAND_USER_MAIN + "?tab=passedTests";

        long testId = Long.parseLong(req.getParameter("testId"));
        long userId = (long)req.getSession().getAttribute("userId");

        TestStatus testStatus = userTestService.getUserTestStatus(userId, testId);
        if(testStatus.equals(TestStatus.PASSED)) {
            PdfBuilderUtil.createResultPdf(resp, userId, testId);
        } else {
            req.setAttribute("commandToGoBack", command);
            command = Path.PAGE_ERROR_PAGE;
        }

        LOGGER.debug("TestResultCommand execution started");
        return new DispatchInfo(false, command);
    }
}
