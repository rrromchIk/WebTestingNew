package com.epam.testing.controller.command.common;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.service.UserService;
import com.epam.testing.util.VerifyRecaptcha;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private final UserService userService = new UserService();
    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("LoginCommand execution started");
        String page = req.getContextPath();

        HttpSession httpSession = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(userService.userExists(login, password)) {
            User user = userService.getUserByLogin(login);
            if(login.equals("admin") && password.equals("admin")) {
                page += Path.COMMAND_ADMIN_MAIN;
            } else {
                page += Path.COMMAND_USER_MAIN;
            }
            LOGGER.info("Login success. Login: {}", user.getLogin());

            httpSession.setAttribute("userId", user.getId());
            httpSession.setAttribute("login", user.getLogin());
            httpSession.setAttribute("userRole", user.getRole());
        } else {
            LOGGER.info("Invalid login or password");
            httpSession.setAttribute("invalid", true);
            page += Path.PAGE_LOGIN;
        }

        LOGGER.debug("LoginCommand execution finished");
        return new DispatchInfo(true, page);
    }
}
