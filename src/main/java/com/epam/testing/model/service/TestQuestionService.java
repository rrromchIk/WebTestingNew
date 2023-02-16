package com.epam.testing.model.service;

import com.epam.testing.model.dao.QuestionDAO;
import com.epam.testing.model.dao.impl.QuestionCorrectAnswersDAOImpl;
import com.epam.testing.model.dao.impl.QuestionDAOImpl;
import com.epam.testing.model.dao.impl.QuestionAnswerVariantsDAOImpl;
import com.epam.testing.model.dto.CheckedAnswer;
import com.epam.testing.model.entity.question.Question;

import java.util.List;
import java.util.stream.Collectors;

public class TestQuestionService {
    private final QuestionDAO questionDAO;
    private final QuestionAnswerVariantsDAOImpl answerVariantDAO;
    private final QuestionCorrectAnswersDAOImpl correctAnswerDAO;
    private final UserAnswerService userAnswerService;

    public TestQuestionService() {
        this.questionDAO = new QuestionDAOImpl();
        this.answerVariantDAO = new QuestionAnswerVariantsDAOImpl();
        this.correctAnswerDAO = new QuestionCorrectAnswersDAOImpl();
        this.userAnswerService = new UserAnswerService();
    }

    public TestQuestionService(QuestionDAO questionDAO,
                               QuestionAnswerVariantsDAOImpl answerVariantDAO,
                               QuestionCorrectAnswersDAOImpl correctAnswerDAO,
                               UserAnswerService userAnswerService) {
        this.questionDAO = questionDAO;
        this.answerVariantDAO = answerVariantDAO;
        this.correctAnswerDAO = correctAnswerDAO;
        this.userAnswerService = userAnswerService;
    }

    public int getAmountOfAddedQuestions(long testId) {
        return questionDAO.getAmountOfRecordsByTestId(testId);
    }

    public List<Question> getQuestionsByTestId(long testId) {
        List<Question> questions = questionDAO.getAllByTestId(testId);
        for(int i = 0; i < questions.size(); i++) {
            questions.get(i).setNumber(i + 1);
        }
        return questions;
    }

    public List<String> getCorrectAnswers(long questionId) {
        return correctAnswerDAO.getAllByQuestionId(questionId);
    }

    public boolean addQuestionAnswer(long questionId, String text) {
        return answerVariantDAO.create(questionId, text);
    }

    public boolean addCorrectAnswer(long questionId, String text) {
        return correctAnswerDAO.create(questionId, text);
    }

    public List<CheckedAnswer> getAnswerVariantsByQuestionIdWithCheckedStatus(long userId, long questionId) {
        List<String> allAnswersToQuestion = answerVariantDAO.getAllByQuestionId(questionId);
        List<String> userAnswers = userAnswerService.getUsersAnswers(userId, questionId);

        return allAnswersToQuestion.stream().map(answerStr -> {
            boolean checked = userAnswers.contains(answerStr);
            return new CheckedAnswer(answerStr, checked);
        }).collect(Collectors.toList());
    }

    public boolean addQuestionToTheTest(long testId, Question question) {
        long id = questionDAO.create(testId, question);
        question.setId(id);
        return id != -1;
    }

    public boolean deleteQuestion(long questionId) {
        return questionDAO.delete(questionId);
    }
}
