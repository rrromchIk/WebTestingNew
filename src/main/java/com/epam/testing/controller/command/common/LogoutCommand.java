package com.epam.testing.controller.command.common;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    @Override
    public DispatchInfo execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("LogoutCommand execution started");
        HttpSession session = request.getSession(false);

        if(session != null) {
            session.setAttribute("userRole", UserRole.GUEST);
            session.setAttribute("login", "");
        }

        LOGGER.debug("LogoutCommand execution finished");
        return new DispatchInfo(false, Path.PAGE_INDEX);
    }
}
