package com.epam.testing.controller.command.client.profile;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class AvatarUploadCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AvatarUploadCommand.class);
    private final UserService userService = new UserService();
    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("AvatarUploadCommand execution starts");
        long userId = (long)req.getSession().getAttribute("userId");

        try {
            Part filePart = req.getPart("file");
            InputStream inputStream = filePart.getInputStream();

            if(inputStream != null) {
                userService.setAvatar(inputStream, userId);
                LOGGER.info("Image upload success. Image name: {}", filePart.getSubmittedFileName());
            } else {
                LOGGER.warn("Image upload failed");
            }
        } catch (IOException | ServletException e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }

        LOGGER.debug("AvatarUploadCommand execution finished");
        return new DispatchInfo(true, req.getContextPath() + Path.COMMAND_USER_PROFILE);
    }
}
