package com.epam.testing.controller.command.client.profile;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.service.UserService;
import com.epam.testing.util.ImageConverterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class);
    private final UserService userService = new UserService();
    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("ProfileCommand execution started");
        String page = Path.PAGE_USER_PROFILE;

        HttpSession httpSession = req.getSession();
        String userLogin = (String)httpSession.getAttribute("login");

        User user = userService.getUserByLogin(userLogin);
        req.setAttribute("fullUser", user);
        req.setAttribute("userAvatar", ImageConverterUtil.getBase64String(user.getAvatar()));

        LOGGER.debug("ProfileCommand execution finished");
        return new DispatchInfo(false, page);
    }

}
