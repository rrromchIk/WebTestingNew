package com.epam.testing.controller.command.client.profile;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetAvatarCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ResetAvatarCommand.class);
    private final UserService userService = new UserService();
    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("ResetAvatarCommand execution starts");
        long userId = (long)req.getSession().getAttribute("userId");

        userService.setAvatar(null, userId);

        LOGGER.debug("ResetAvatarCommand execution finished");
        return new DispatchInfo(true, req.getContextPath() + Path.COMMAND_USER_PROFILE);
    }
}
