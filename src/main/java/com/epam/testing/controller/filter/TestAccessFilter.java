package com.epam.testing.controller.filter;

import com.epam.testing.controller.Path;
import com.epam.testing.model.entity.test.TestStatus;
import com.epam.testing.model.service.UserTestService;
import com.epam.testing.util.CalculateTestResultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * TestAccess filter. Checks for user permission for test.
 *
 * @author rom4ik
 */
@WebFilter(filterName = "TestAccessFilter",
        urlPatterns = "/controller")
public class TestAccessFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(TestAccessFilter.class);
    private final UserTestService userTestService = new UserTestService();

    @Override
    public void init(FilterConfig config) {
        LOGGER.debug("TestAccess filter initialization");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(!action.equals("passTest")) {
            chain.doFilter(request, response);
        } else if(accessAllowed(request)) {
            chain.doFilter(request, response);
        } else {
            String errorMessages = "You do not have permission to access the test";
            request.setAttribute("commandToGoBack", Path.COMMAND_USER_MAIN);
            request.setAttribute("errorMessage", errorMessages);
            request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
        }
    }

    /**
     * @param request contains info about required test and related resources
     * @return true if access permitted, else false
     */
    private boolean accessAllowed(ServletRequest request) {
        LOGGER.debug("Test access filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession();
        if(session == null) {
            LOGGER.warn("Invalid session");
            return false;
        }

        long userId = (long)session.getAttribute("userId");
        long testId = Long.parseLong(httpRequest.getParameter("testId"));

        TestStatus testStatus = userTestService.getUserTestStatus(userId, testId);
        boolean result;
        if(testStatus.equals(TestStatus.NOT_STARTED)) {
            LOGGER.warn("Access invalid. Reason: test status - NOT_STARTED");
            result = false;
        } else if(testStatus.equals(TestStatus.STARTED)) {
            long remainingTime = userTestService.getRemainingTime(userId, testId);
            result = remainingTime > 0;
            if(!result) {
                float testResult = CalculateTestResultService.getTestResult(testId, userId);
                userTestService.addResultAndEndingTime(userId, testId, testResult, Timestamp.valueOf(LocalDateTime.now()));
                userTestService.updateUserTestStatus(userId, testId, TestStatus.PASSED);
            } else {
                LOGGER.warn("Access success. Remaining time for test -> {}", remainingTime);
            }
        } else {
            LOGGER.warn("Access invalid. Reason: test status - PASSED");
            result = false;
        }
        return result;
    }

    @Override
    public void destroy() {
        LOGGER.debug("Test access filter destroyed");
    }
}
