package com.epam.testing.controller.command.client.testpassing;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.service.UserAnswerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitAnswersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SubmitAnswersCommand.class);
    private final UserAnswerService userAnswerService = new UserAnswerService();
    private static final int MAX_AMOUNT_OF_ANSWER_VARIANTS = 10;

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("SubmitAnswersCommand execution started");
        String page = req.getContextPath() + Path.COMMAND_USER_PASS_TEST;

        long questionId = Long.parseLong(req.getParameter("questionId"));
        long userId = (long) req.getSession().getAttribute("userId");
        long testId = Long.parseLong(req.getParameter("testId"));

        int i = 0;
        userAnswerService.deleteUserAnswers(userId, questionId);
        while(i != MAX_AMOUNT_OF_ANSWER_VARIANTS) {
            String text = req.getParameter("answer" + i);
            i++;
            LOGGER.info("User answer: {}", text);
            if(text != null) {
                userAnswerService.addUserAnswer(userId, questionId, text);
            }
        }

        page += "&testId=" + testId;
        page += "&renderQuestion=" + questionId;

        LOGGER.debug("SubmitAnswersCommand execution finished");
        return new DispatchInfo(true, page);
    }
}
