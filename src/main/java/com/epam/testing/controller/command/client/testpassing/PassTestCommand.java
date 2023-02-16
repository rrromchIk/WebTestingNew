package com.epam.testing.controller.command.client.testpassing;

import com.epam.testing.controller.DispatchInfo;
import com.epam.testing.controller.Path;
import com.epam.testing.controller.command.Command;
import com.epam.testing.model.dto.CheckedAnswer;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.question.QuestionType;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.service.TestQuestionService;
import com.epam.testing.model.service.TestsService;
import com.epam.testing.model.service.UserTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class PassTestCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PassTestCommand.class);
    private final TestsService testsService = new TestsService();
    private final TestQuestionService testQuestionService = new TestQuestionService();
    private final UserTestService userTestService = new UserTestService();

    @Override
    public DispatchInfo execute(HttpServletRequest req, HttpServletResponse resp) {
        LOGGER.debug("PassTestCommand execution started");
        String page = Path.PAGE_USER_TEST_PASSING;
        HttpSession httpSession = req.getSession();

        String questionToRender = req.getParameter("renderQuestion");
        long userId = (long)httpSession.getAttribute("userId");
        long testId = Long.parseLong(req.getParameter("testId"));

        Test test = testsService.getTestById(testId);
        List<Question> questions = testQuestionService.getQuestionsByTestId(testId);

        if(!questions.isEmpty()) {
            Question currentQuestion = getCurrentQuestion(questions, questionToRender);

            List<CheckedAnswer> answerVariants = testQuestionService
                    .getAnswerVariantsByQuestionIdWithCheckedStatus(userId, currentQuestion.getId());

            String questionType = getQuestionType(currentQuestion);
            long remainingTime = userTestService.getRemainingTime(userId, testId);

            req.setAttribute("timer", remainingTime);
            req.setAttribute("questionType", questionType);
            req.setAttribute("answers", answerVariants);
            req.setAttribute("test", test);
            req.setAttribute("questions", questions);
            req.setAttribute("questionAmount", questions.size());
            req.setAttribute("currentQuestion", currentQuestion);
        } else {
            page = Path.PAGE_ERROR_PAGE;
            String errorMessage = "No questions in the test added. Try later.";
            req.setAttribute("commandToGoBack", Path.COMMAND_USER_MAIN);
            req.setAttribute("errorMessage", errorMessage);

            LOGGER.warn(errorMessage);
        }

        LOGGER.debug("PassTestCommand execution finished");
        return new DispatchInfo(false, page);
    }

    private boolean isQuestionToRenderValid(String questionToRender) {
        try {
            Integer.parseInt(questionToRender);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private String getQuestionType(Question question) {
        String result;
        if(question.getType().equals(QuestionType.MULTIPLE_CORRECT_ANSWERS)) {
            result = "checkbox";
        } else {
            result = "radio";
        }
        return result;
    }

    private Question getCurrentQuestion(List<Question> questions, String questionToRender) {
        Question currentQuestion = questions.get(0);
        if(isQuestionToRenderValid(questionToRender)) {
            Optional<Question> opt = questions.stream()
                    .filter(question -> question.getId() == Integer.parseInt(questionToRender))
                    .findFirst();
            if(opt.isPresent())
                currentQuestion = opt.get();
        }
        return currentQuestion;
    }
}
