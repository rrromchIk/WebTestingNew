package com.epam.testing.controller.command.client.profile;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditProfileCommand.class);
    private final UserService userService = new UserService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("EditProfileCommand execution started");
        String page = req.getContextPath() + Path.COMMAND_USER_PROFILE;
        boolean redirect = true;

        String login = req.getParameter("login");
        String newName = req.getParameter("name");
        String newSurname= req.getParameter("surname");
        String newEmail = req.getParameter("email");

        User user = userService.getUserByLogin(login);
        user.setName(newName);
        user.setSurname(newSurname);
        user.setEmail(newEmail);

        if(!userService.updateUser(user)) {
            String errorMessage = "Can not update";
            req.setAttribute("commandToGoBack", Path.COMMAND_USER_PROFILE);
            req.setAttribute("errorMessage", errorMessage);
            page = Path.PAGE_ERROR_PAGE;
            redirect = false;

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("EditProfileCommand execution finished");
        return new DispatchInfo(redirect, page);
    }
}
