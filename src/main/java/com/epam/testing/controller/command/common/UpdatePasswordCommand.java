package com.epam.testing.controller.command.common;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.entity.user.UserToken;
import com.epam.testing.model.service.UserService;
import com.epam.testing.model.service.UserTokenService;
import com.epam.testing.util.CryptoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class UpdatePasswordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdatePasswordCommand.class);
    private final UserTokenService userTokenService = new UserTokenService();
    private final UserService userService = new UserService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("UpdatePasswordCommand execution starts");
        String command = req.getContextPath() + Path.PAGE_LOGIN;
        HttpSession session = req.getSession();

        String newPassword = req.getParameter("newPassword");
        String token = req.getParameter("token");
        LOGGER.info("Token: {}", token);

        User user = getUserByToken(token);
        if(user != null && updatePassword(user, newPassword)) {
            LOGGER.warn("Password update success");
            session.setAttribute("success", true);
            session.setAttribute("msg", "resetPassword.successFeedback");
        } else {
            LOGGER.warn("Failed to update password");
            session.setAttribute("invalid", true);
            session.setAttribute("msg", "resetPassword.faultFeedback");
            command = Path.PAGE_FORGOT_PASSWORD;
        }

        LOGGER.debug("UpdatePasswordCommand execution finished");
        return new DispatchInfo(true, command);
    }

    private User getUserByToken(String token) {
        UserToken userToken = userTokenService.getUserToken(CryptoUtil.getHashedToken(token));
        if(userToken != null && userToken
                .getExpirationDate()
                .toLocalDateTime()
                .isAfter(LocalDateTime.now())) {
            return userService.getUserById(userToken.getUserId());
        } else {
            return null;
        }
    }

    private boolean updatePassword(User user, String newPassword) {
        return userService.updatePassword(newPassword, user.getId());
    }
}
