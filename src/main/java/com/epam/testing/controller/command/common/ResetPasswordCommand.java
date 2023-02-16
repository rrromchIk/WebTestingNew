package com.epam.testing.controller.command.common;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.entity.user.UserToken;
import com.epam.testing.model.service.UserService;
import com.epam.testing.model.service.UserTokenService;
import com.epam.testing.util.CryptoUtil;
import com.epam.testing.util.EmailSenderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ResetPasswordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ResetPasswordCommand.class);
    private final UserService userService = new UserService();
    private final UserTokenService userTokenService = new UserTokenService();
    private static final Integer TOKEN_EXPIRE_TIME_IN_MINUTES = 15;
    private static final String HOST = "localhost:8080";

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("ResetPasswordCommand execution starts");
        HttpSession session = req.getSession();
        String email = req.getParameter("email");

        User user = userService.getUserByEmail(email);
        if(user == null) {
            session.setAttribute("invalid", true);
            LOGGER.warn("No user with such email");
        } else {
            String token = CryptoUtil.generateToken();

            userTokenService.addUserToken(createUserToken(user, token));
            String message = createMessage(req, token);
            String subject = "WebTesting";

            if(EmailSenderUtil.sendEmail(email, subject, message)) {
                session.setAttribute("success", true);
            } else {
                session.setAttribute("invalid", true);
                session.setAttribute("msg", "forgotPasswordForm.fail.label");
            }
        }

        LOGGER.debug("ResetPasswordCommand execution finished");
        return new DispatchInfo(true, Path.PAGE_FORGOT_PASSWORD);
    }

    private String createMessage(HttpServletRequest request, String token) {
        String url = String.format("http://%s%s/%s?token=%s",
                HOST,
                request.getContextPath(),
                Path.PAGE_CHANGE_PASSWORD,
                token);
        return "Click to reset password: " + url;
    }

    private UserToken createUserToken(User user, String token) {
        String hashedToken = CryptoUtil.getHashedToken(token);
        long userId = user.getId();
        Timestamp expireTime = Timestamp.valueOf(
                LocalDateTime.now()
                        .plusMinutes(TOKEN_EXPIRE_TIME_IN_MINUTES)
        );

        return new UserToken.UserTokenBuilder()
                .userId(userId)
                .token(hashedToken)
                .expirationDate(expireTime)
                .build();
    }
}
