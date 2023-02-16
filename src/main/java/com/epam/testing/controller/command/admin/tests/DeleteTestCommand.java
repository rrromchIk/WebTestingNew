package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.service.TestsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTestCommand.class);
    private final TestsService testsService = new TestsService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("DeleteTestCommand execution started");
        int activePage = (int)req.getSession().getAttribute("activePage");
        long testId = Long.parseLong(req.getParameter("testId"));

        boolean redirect = true;
        String page = req.getContextPath() + Path.COMMAND_ADMIN_MAIN + "&page=" + activePage;

        if(!testsService.deleteTest(testId)) {
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "Unable to delete test";
            req.setAttribute("commandToGoBack", page);
            req.setAttribute("errorMessage", errorMessage);
            redirect = false;

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("DeleteTestCommand execution finished");
        return new DispatchInfo(redirect, page);
    }
}
