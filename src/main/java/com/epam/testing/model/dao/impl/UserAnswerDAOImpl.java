package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.dao.UserAnswerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** UserAnswerDAOImpl class
 *
 * @author  rom4ik
 */

public class UserAnswerDAOImpl implements UserAnswerDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserAnswerDAOImpl.class);
    private final DataSource datasource = DataSource.getInstance();

    /**
     * Select User's answers
     *
     * @param userId for identification
     * @param questionId for identification
     * @return not empty list if answers exist, else empty list.
     */
    @Override
    public List<String> getUserAnswers(long userId, long questionId) {
        List<String> userAnswers = new ArrayList<>();
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UserAnswerQueries.GET.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, questionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userAnswers.add(resultSet.getString(UserAnswerFields.TEXT.FIELD));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return userAnswers;
    }

    /**
     * Create User answer in database.
     *
     * @param userId for user identification.
     * @param questionId for question identification.
     * @param text represents answer content.
     * @return true create success, else false.
     */
    @Override
    public boolean create(long userId, long questionId, String text) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserAnswerQueries.CREATE.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, questionId);
            statement.setString(3, text);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete User answer by userId and questionId
     *
     * @param userId and
     * @param questionId for answer identification
     * @return true if answer was deleted. False if not.
     */
    @Override
    public boolean delete(long userId, long questionId) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserAnswerQueries.DELETE.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, questionId);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Contains all used queries for user_answer table
     */
    enum UserAnswerQueries {
        GET("SELECT * FROM user_answer WHERE user_id = ? AND question_id = ?"),
        CREATE("INSERT INTO user_answer(user_id, question_id, text) VALUES(?, ?, ?)"),
        DELETE("DELETE FROM user_answer WHERE user_id = ? AND question_id = ?");

        final String QUERY;
        UserAnswerQueries(String query) {
            this.QUERY = query;
        }
    }

    /**
     * Contains all fields in user_answer table
     */
    enum UserAnswerFields {
        USER_ID("user_id"),
        QUESTION_ID("question_id"),
        TEXT("text");

        final String FIELD;
        UserAnswerFields(String field) {
            this.FIELD = field;
        }
    }
}






