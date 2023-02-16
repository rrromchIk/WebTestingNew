package com.epam.testing.controller.command.admin.tests;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTestCommand.class);

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("AddTestCommand execution started");
        String page = Path.PAGE_ADD_TEST;

        LOGGER.debug("AddTestCommand execution finished");
        return new DispatchInfo(false, page);
    }
}
