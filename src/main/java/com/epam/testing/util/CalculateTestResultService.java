package com.epam.testing.util;

import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.question.QuestionType;
import com.epam.testing.model.service.TestQuestionService;
import com.epam.testing.model.service.UserAnswerService;

import java.util.ArrayList;
import java.util.List;

/**
 * CalculateTestResultService util. Calculates total result
 * of user passed test in percentage of correct answers
 *
 * @author rom4ik
 */
public class CalculateTestResultService {
    private static final TestQuestionService testQuestionService = new TestQuestionService();
    private static final UserAnswerService userAnswerService = new UserAnswerService();

    /**
     * Don't let anyone instantiate this class.
     */
    private CalculateTestResultService() {
    }

    /**
     * @param testId for Test identification
     * @param userId for User identification
     * @return Test result in percents
     */
    public static float getTestResult(long testId, long userId) {
        List<Question> questions = testQuestionService.getQuestionsByTestId(testId);
        int totalMaxScore = getTotalMaxScore(questions);
        int actualScore = getActualScore(questions, userId);
        return (actualScore * 100) / (float)(totalMaxScore);
    }


    /**
     * @param question represents Question entity which score has to be calculated
     * @param userId for User identification
     * @return user score on this question
     */
    public static int getQuestionScore(Question question, long userId) {
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        return getActualScore(questions, userId);
    }

    /**
     * @param questionId for Question identification
     * @param answer that has to be checked in terms of correctness
     * @return true if answer is correct, else false
     */
    public static boolean isAnswerCorrect(long questionId, String answer) {
        List<String> questionCorrectAnswers = testQuestionService
                .getCorrectAnswers(questionId);
        return questionCorrectAnswers.contains(answer);
    }

    private static int getTotalMaxScore(List<Question> questions) {
        int totalScore = 0;
        for (Question question : questions) {
            totalScore += question.getMaxScore();
        }
        return totalScore;
    }

    private static int getActualScore(List<Question> questions, long userId) {
        int actualScore = 0;
        List<String> questionCorrectAnswers;
        List<String> userAnswers;

        for (Question question : questions) {
            long questionId = question.getId();
            questionCorrectAnswers = testQuestionService
                    .getCorrectAnswers(questionId);
            userAnswers = userAnswerService
                    .getUsersAnswers(userId, questionId);

            if (question.getType().equals(QuestionType.SINGLE_CORRECT_ANSWER)) {
                actualScore += calculateScoreOfSingleAnswerQuestion(question.getMaxScore(),
                        questionCorrectAnswers, userAnswers);
            } else {
                actualScore += calculateScoreOfMultipleAnswerQuestion(question.getMaxScore(),
                        questionCorrectAnswers, userAnswers);
            }
        }


        return actualScore;
    }

    private static int calculateScoreOfSingleAnswerQuestion(int maxScore,
                                                            List<String> questionCorrectAnswers,
                                                            List<String> userAnswers) {
        int score = 0;
        if(questionCorrectAnswers.isEmpty() && userAnswers.isEmpty()) {
            return maxScore;
        }
        if(!userAnswers.isEmpty()) {
            score += questionCorrectAnswers.contains(userAnswers.get(0)) ? maxScore : 0;
        }
        return score;
    }

    private static int calculateScoreOfMultipleAnswerQuestion(int maxScore,
                                                              List<String> questionCorrectAnswers,
                                                            List<String> userAnswers) {
        int score = 0;
        if(questionCorrectAnswers.isEmpty() && userAnswers.isEmpty()) {
            return maxScore;
        }
        int amountOfCorrectAnswers = questionCorrectAnswers.size();
        int amountOfUserCorrectAnswers = 0;
        for(String userAnswer : userAnswers) {
            if(questionCorrectAnswers.contains(userAnswer)) {
                amountOfUserCorrectAnswers++;
            } else {
                amountOfUserCorrectAnswers = 0;
                break;
            }
        }

        if(amountOfUserCorrectAnswers == amountOfCorrectAnswers) {
            score += maxScore;
        }

        return score;
    }
}
