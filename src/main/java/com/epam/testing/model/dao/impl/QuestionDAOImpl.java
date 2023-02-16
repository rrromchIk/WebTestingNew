package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.dao.QuestionDAO;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.question.QuestionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAOImpl implements QuestionDAO {
    private static final Logger LOGGER = LogManager.getLogger(QuestionDAOImpl.class);
    private final DataSource datasource;

    public QuestionDAOImpl() {
        datasource = DataSource.getInstance();
    }

    public QuestionDAOImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Select amount of records by test id.
     *
     * @return number of records
     */
    @Override
    public int getAmountOfRecordsByTestId(long testId) {
        int amount = 0;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     QuestionQueries.GET_AMOUNT_BY_TEST_ID.QUERY)) {
            statement.setLong(1, testId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * Select Question by test id.
     *
     * @param testId for select.
     * @return valid list of questions if they exist. If not return empty list.
     */
    public List<Question> getAllByTestId(long testId) {
        List<Question> questionArr = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QuestionDAOImpl.QuestionQueries.GET_BY_TEST_ID.QUERY)) {
            statement.setLong(1, testId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                questionArr.add(mapQuestion(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return questionArr;
    }

    /** Map Question from ResultSet.
     *
     * @param resultSet for getting info.
     * @return entity Question from database.
     * @throws SQLException if something went wrong.
     */
    public static Question mapQuestion(ResultSet resultSet) throws SQLException{
        Question question = new Question.QuestionBuilder()
                .text(resultSet.getString(QuestionFields.TEXT.FIELD))
                .type(QuestionType.getEnum(resultSet.getString(QuestionFields.TYPE.FIELD)))
                .maxScore(resultSet.getInt(QuestionFields.MAX_SCORE.FIELD))
                .build();
        question.setId(resultSet.getLong(QuestionFields.ID.FIELD));

        return question;
    }


    /**
     * Create Question in database.
     *
     * @param testId for identification.
     * @param question fot text and type to add.
     * @return false if Question already exist. If creating success true.
     */
    public long create(long testId, Question question) {
        long result = -1;
        try(Connection connection  = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                        QuestionQueries.INSERT.QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, testId);
            statement.setString( 2, question.getText());
            statement.setString( 3, question.getType().getValue());
            statement.setInt(4, question.getMaxScore());
            if(statement.executeUpdate() <= 0) {
                throw new SQLException();
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                result = generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete Question by name.
     *
     * @param id for delete.
     * @return true if Question was deleted. False if Question not exist.
     */
    public boolean delete(long id) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(QuestionDAOImpl.QuestionQueries.DELETE.QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Contains all used queries for question table
     */
    enum QuestionQueries {
        INSERT("INSERT INTO question(test_id, text, type, max_score) values(?, ?, ?, ?)"),
        UPDATE("UPDATE question SET text = ? WHERE id = ?"),
        GET_BY_TEST_ID("SELECT * FROM question WHERE test_id = ?"),
        DELETE("DELETE FROM question WHERE id = ?"),
        GET_AMOUNT_BY_TEST_ID("SELECT COUNT(id) FROM question WHERE test_id = ?");

        final String QUERY;

        QuestionQueries(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    /**
     * Contains all fields in question table
     */
    enum QuestionFields {
        ID("id"),
        TEST_ID("test_id"),
        TYPE("type"),
        TEXT("text"),
        MAX_SCORE("max_score");

        final String FIELD;
        QuestionFields(String field) {
            this.FIELD = field;
        }
    }
}
