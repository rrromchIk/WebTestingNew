package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.dao.QuestionAnswerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerVariantsDAOImpl implements QuestionAnswerDAO {
    private static final Logger LOGGER = LogManager.getLogger(QuestionAnswerVariantsDAOImpl.class);
    private final DataSource datasource;

    public QuestionAnswerVariantsDAOImpl() {
        datasource = DataSource.getInstance();
    }

    public QuestionAnswerVariantsDAOImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Select all answers variants of question.
     *
     * @param id for identification
     * @return valid list of answers if they exist. If not return empty list.
     */
    public List<String> getAllByQuestionId(long id) {
        List<String> answers = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(AnswerQueries.GET_ALL_BY_ID.QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                answers.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return answers;
    }

    /**
     * Create Correct Answer in database.
     *
     * @param questionId for identification.
     * @param text for answer text
     * @return false if Correct Answer already exist. If creating success - true.
     */
    public boolean create(long questionId, String text) {
        boolean result = false;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(AnswerQueries.INSERT.QUERY)) {
            statement.setLong( 1, questionId);
            statement.setString(2, text);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Contains all used queries for question_answer_variants table
     */
    enum AnswerQueries {
        INSERT("INSERT INTO question_answer_variants(question_id, text) VALUES(?, ?)"),
        GET_ALL_BY_ID("SELECT text FROM question_answer_variants WHERE question_id = ?");

        public final String QUERY;
        AnswerQueries(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
