package com.epam.testing.model.dao.impl;

import com.epam.testing.model.entity.test.TestDifficulty;
import com.epam.testing.model.entity.test.Test;
import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.dao.TestDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** TestDAOImpl class
 *
 * @author rom4ik
 */

public class TestDAOImpl implements TestDAO {
    private static final Logger LOGGER = LogManager.getLogger(TestDAOImpl.class);
    private final DataSource datasource;

    public TestDAOImpl() {
        datasource = DataSource.getInstance();
    }

    public TestDAOImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    /**
     * Select Test by name.
     *
     * @param name for select.
     * @return valid entity if it exists. If entity does not exist return null.
     */
    public Test getByName(String name) {
        Test test = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TestDAOImpl.TestQueries.GET_BY_NAME.QUERY)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                test = mapTest(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return test;
    }

    /**
     * Select all Tests sorted by number of questions.
     *
     * @param limit and
     * @param offset for pagination implementation.
     * @return not empty List of Test if exists. If not empty List.
     */
    @Override
    public List<Test> getAllSortedByNumberOfQuestions(int limit, int offset) {
        return getMany(TestQueries.GET_ALL_SORTED_BY_NUMBER_OF_QUESTIONS.QUERY, limit, offset);
    }

    /**
     * Select all Tests sorted by name.
     *
     * @param limit and
     * @param offset for pagination implementation.
     * @return not empty List of Test if exists. If not empty List.
     */
    @Override
    public List<Test> getAllSortedByName(int limit, int offset) {
        return getMany(TestQueries.GET_ALL_SORTED_BY_NAME.QUERY, limit, offset);
    }

    /**
     * Select all Tests sorted by difficulty.
     *
     * @param limit and
     * @param offset for pagination implementation.
     * @return not empty List of Test if exists. If not empty List.
     */
    @Override
    public List<Test> getAllSortedByDifficulty(int limit, int offset) {
        return getMany(TestQueries.GET_ALL_SORTED_BY_DIFFICULTY.QUERY, limit, offset);
    }

    /**
     * Select all Tests on particular subject.
     *
     * @param limit and
     * @param offset for pagination implementation.
     * @return not empty List of Test if exists. If not empty List.
     */
    @Override
    public List<Test> getAllOnParticularSubject(String subject, int limit, int offset) {
        List<Test> testArr = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     TestQueries.GET_ALL_ON_PARTICULAR_SUBJECT.QUERY)) {
            statement.setString(1, subject);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                testArr.add(mapTest(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return testArr;
    }

    /**
     * Select all subjects.
     *
     * @return not empty List of subjects if exists. If not empty List.
     */
    @Override
    public List<String> getAllTestsSubjects() {
        List<String> subjects = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TestQueries.GET_ALL_SUBJECTS.QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                subjects.add(resultSet.getString(TestFields.SUBJECT.FIELD));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return subjects;
    }

    /**
     * Select amount of records on particular subject.
     *
     * @return number of records
     */
    @Override
    public int getAmountOnParticularSubject(String subject) {
        int amount = 0;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     TestQueries.GET_AMOUNT_OF_RECORDS_ON_PART_SUBJ.QUERY)) {
            statement.setString(1, subject);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * Select amount of records.
     *
     * @return number of records
     */
    @Override
    public int getAmountOfRecords() {
        int amount = 0;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     TestQueries.GET_AMOUNT_OF_RECORDS.QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * Select all Tests.
     *
     * @return valid list of tests if they exist. If not return empty list.
     */
    @Override
    public List<Test> getAll(int limit, int offset) {
        return getMany(TestQueries.GET_ALL.QUERY, limit, offset);
    }

    private List<Test> getMany(String query, int limit, int offset) {
        List<Test> testArr = new ArrayList<>();
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                testArr.add(mapTest(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return testArr;
    }

    /**
     * Select Test by id.
     *
     * @param id for select.
     * @return valid entity if it exists. If entity does not exist return null.
     */
    @Override
    public Test getById(long id) {
        Test test = null;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TestDAOImpl.TestQueries.GET_BY_ID.QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                test = mapTest(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return test;
    }

    /** Map Test from ResultSet
     *
     * @param resultSet for getting info
     * @return entity Test from database
     * @throws SQLException if something went wrong
     */
    public static Test mapTest(ResultSet resultSet) throws SQLException{
        Test test = new Test.TestBuilder()
                .name(resultSet.getString(TestDAOImpl.TestFields.NAME.FIELD))
                .subject(resultSet.getString(TestDAOImpl.TestFields.SUBJECT.FIELD))
                .difficulty(TestDifficulty.getEnum(resultSet.getInt(TestDAOImpl.TestFields.DIFFICULTY.FIELD)))
                .numberOfQuestions(resultSet.getInt(TestDAOImpl.TestFields.NUMBER_OF_QUESTIONS.FIELD))
                .duration(resultSet.getInt(TestDAOImpl.TestFields.DURATION.FIELD))
                .build();
        test.setId(resultSet.getInt(TestDAOImpl.TestFields.ID.FIELD));

        return test;
    }

    /**
     * Create Test in database.
     *
     * @param test for create.
     * @return -1 if Test already exist. If creating success - Test's id.
     */
    @Override
    public long create(Test test) {
        long result = -1;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    TestQueries.INSERT.QUERY, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, test.getName());
            statement.setString(2, test.getSubject());
            statement.setInt(3, test.getDifficulty().getValue());
            statement.setInt(4, test.getDuration());
            statement.setInt(5, test.getNumberOfQuestions());
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
     * Update Test's info by name.
     *
     * @param test new test's state.
     * @return True if success. False if fails.
     */
    @Override
    public boolean update(Test test) {
        boolean result = false;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(TestQueries.UPDATE.QUERY)) {
            statement.setString(1, test.getName());
            statement.setString(2, test.getSubject());
            statement.setInt(3, test.getDifficulty().getValue());
            statement.setInt(4, test.getDuration());
            statement.setInt(5, test.getNumberOfQuestions());
            statement.setString(6, test.getName());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete Test by name
     *
     * @param id for delete.
     * @return true if Test was deleted. False if Test not exist.
     */
    public boolean delete(long id) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(TestDAOImpl.TestQueries.DELETE.QUERY)) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Contains all used queries for test table
     */
    enum TestQueries{
        INSERT("INSERT INTO test(name, subject, difficulty, duration, number_of_questions) VALUES(?, ?, ?, ?, ?)"),
        UPDATE("UPDATE test SET name = ?, subject = ?, difficulty = ?, duration = ?, number_of_questions = ?" +
                " WHERE name = ?"),
        DELETE("DELETE FROM test WHERE id = ?"),
        GET_ALL("SELECT * FROM test LIMIT ? OFFSET ?"),
        GET_BY_ID("SELECT * FROM test WHERE id = ?"),
        GET_BY_NAME("SELECT * FROM test WHERE name = ?"),

        GET_ALL_SORTED_BY_NAME("SELECT * FROM test ORDER BY name LIMIT ? OFFSET ?"),
        GET_ALL_SORTED_BY_DIFFICULTY("SELECT * FROM test ORDER BY difficulty LIMIT ? OFFSET ?"),
        GET_ALL_SORTED_BY_NUMBER_OF_QUESTIONS("SELECT * FROM test ORDER BY number_of_questions LIMIT ? OFFSET ?"),
        GET_ALL_ON_PARTICULAR_SUBJECT("SELECT * FROM test where subject = ? LIMIT ? OFFSET ?"),

        GET_ALL_SUBJECTS("SELECT subject FROM test"),
        GET_AMOUNT_OF_RECORDS("SELECT COUNT(name) FROM test"),
        GET_AMOUNT_OF_RECORDS_ON_PART_SUBJ("SELECT COUNT(name) FROM test WHERE subject = ?");

        final String QUERY;
        TestQueries(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    /**
     * Contains all fields in test table
     */
    enum TestFields {
        ID("id"),
        NAME("name"),
        SUBJECT("subject"),
        DIFFICULTY ("difficulty"),
        DURATION("duration"),
        NUMBER_OF_QUESTIONS("number_of_questions");

        final String FIELD;
        TestFields(String field) {
            this.FIELD = field;
        }
    }
}
