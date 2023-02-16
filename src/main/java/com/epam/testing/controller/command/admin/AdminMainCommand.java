package com.epam.testing.controller.command.admin;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.service.TestsService;
import com.epam.testing.model.service.UserService;
import com.epam.testing.util.PaginationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AdminMainCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AdminMainCommand.class);
    private final UserService userService = new UserService();
    private final TestsService testsService = new TestsService();
    private static final Integer RECORDS_ON_PAGE_LIMIT = 3;

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("AdminMainCommand execution started");
        String page = Path.PAGE_ADMIN_MAIN;
        String tab = req.getParameter("tab");

        if(tab == null || tab.isEmpty()) {
            tab = "tests";
        }

        if(tab.equals("tests")) {
            processTestsTab(req);
        } else if(tab.equals("users")) {
            processUsersTab(req);
        } else {
            processTestsTab(req);
        }

        LOGGER.debug("AdminMainCommand execution finished");
        return new DispatchInfo(false, page);
    }

    private void processTestsTab(HttpServletRequest req) {
        int totalNumOfTests = testsService.getAmountOfTests();
        int amountOfPages = PaginationService.getNumberOfPages(RECORDS_ON_PAGE_LIMIT, totalNumOfTests);
        String page = req.getParameter("page");
        int pageNumber = PaginationService.getValidPageNumber(page, totalNumOfTests, RECORDS_ON_PAGE_LIMIT);
        int offset = PaginationService.getOffsetOnCertainPage(RECORDS_ON_PAGE_LIMIT, pageNumber);

        List<Test> tests = testsService.getAllTests(RECORDS_ON_PAGE_LIMIT, offset);
        req.setAttribute("tests", tests);
        req.setAttribute("activeTab", "tests");
        req.setAttribute("amountOfPages", amountOfPages);

        HttpSession session = req.getSession();
        session.setAttribute("activePage", pageNumber);
    }

    private void processUsersTab(HttpServletRequest req) {
        int totalNumOfUsers = userService.getAmountOfUsers();
        int amountOfPages = PaginationService.getNumberOfPages(RECORDS_ON_PAGE_LIMIT, totalNumOfUsers);
        String page = req.getParameter("page");
        int pageNumber = PaginationService.getValidPageNumber(page, totalNumOfUsers, RECORDS_ON_PAGE_LIMIT);
        int offset = PaginationService.getOffsetOnCertainPage(RECORDS_ON_PAGE_LIMIT, pageNumber);

        List<User> users = userService.getAllUsers(RECORDS_ON_PAGE_LIMIT, offset);
        req.setAttribute("users", users);
        req.setAttribute("activeTab", "users");
        req.setAttribute("amountOfPages", amountOfPages);

        HttpSession session = req.getSession();
        session.setAttribute("activePage", pageNumber);
    }
}
