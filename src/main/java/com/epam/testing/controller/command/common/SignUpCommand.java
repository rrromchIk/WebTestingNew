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

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);
    private final UserService userService = new UserService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("SignUpCommand execution started");

        String page = req.getContextPath();
        HttpSession session = req.getSession();
        User user = new User.UserBuilder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .email(req.getParameter("email"))
                .build();

        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
        boolean verifyReCaptcha = VerifyRecaptcha.verify(gRecaptchaResponse);
        if(!verifyReCaptcha) {
            session.setAttribute("invalid", true);
            session.setAttribute("msg", "validation.captcha.notVerified");
            page += Path.PAGE_SIGNUP;
            return new DispatchInfo(true, page);
        }

        if(userService.addUser(user)) {
            LOGGER.info("Sign up success");
            session.setAttribute("success", true);
            session.setAttribute("msg", "validation.signUpSuccess");
            page += Path.PAGE_LOGIN;
        } else {
            LOGGER.info("Sign up fault: login already in use");
            session.setAttribute("invalid", true);
            session.setAttribute("msg", "validation.loginAlreadyInUser");
            page += Path.PAGE_SIGNUP;
        }

        LOGGER.debug("SignUpCommand execution finished");
        return new DispatchInfo(true, page);
    }
}
