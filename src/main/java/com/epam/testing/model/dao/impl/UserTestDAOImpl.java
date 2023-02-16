package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.dao.UserTestDAO;
import com.epam.testing.model.entity.test.TestDifficulty;
import com.epam.testing.model.entity.test.TestInfo;
import com.epam.testing.model.entity.test.TestStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** UserTestDAOImpl class
 *
 * @author  rom4ik
 */

public class UserTestDAOImpl implements UserTestDAO {
    private static final Logger LOGGER = LogManager.getLogger(UserTestDAOImpl.class);
    private final DataSource datasource;

    public UserTestDAOImpl() {
        datasource = DataSource.getInstance();
    }

    public UserTestDAOImpl(DataSource datasource) {
        this.datasource = datasource;
    }

    public int getAmountOfRecords(long userId) {
        int amount = 0;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     UserTestQueries.GET_AMOUNT_OF_RECORDS.QUERY)) {
            statement.setLong(1, userId);
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
     * Creating a connection between test and user
     * @param userId for user identification
     * @param testId for test identification
     * @param startingTime identifies starting time
     * @return true if creating success, else false
     */
    @Override
    public boolean create(long userId, long testId, Timestamp startingTime) {
        boolean result = false;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UserTestQueries.CREATE.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, testId);
            statement.setTimestamp(3, startingTime);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Adding Test result and ending time.
     *
     * @param userId for user identification
     * @param testId for Test identification
     * @param testResult is result
     * @param endingTime is ending time
     * @return True if success. False if fails.
     */
    @Override
    public boolean addResultAndEndingTime(long userId, long testId, Float testResult, Timestamp endingTime) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserTestQueries.ADD_RESULT_AND_ENDING_TIME.QUERY)) {
            statement.setFloat(1, testResult);
            statement.setTimestamp(2, endingTime);
            statement.setLong(3, userId);
            statement.setLong(4, testId);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Select all Tests info of User
     *
     * @param userId for identification
     * @return not empty list of entities if they exist, else empty list.
     */
    @Override
    public List<TestInfo> getTestsInfo(long userId, int limit, int offset) {
        List<TestInfo> userTestsInfo = new ArrayList<>();
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UserTestQueries.GET_TESTS_INFO.QUERY)) {
            statement.setLong(1, userId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userTestsInfo.add(new TestInfo.TestInfoBuilder()
                        .userId(resultSet.getLong(UserTestFields.USER_ID.FIELD))
                        .testId(resultSet.getLong(UserTestFields.TEST_ID.FIELD))
                        .testName(resultSet.getString(TestDAOImpl.TestFields.NAME.FIELD))
                        .testSubject(resultSet.getString(TestDAOImpl.TestFields.SUBJECT.FIELD))
                        .testDifficulty(TestDifficulty.getEnum(resultSet.getInt(TestDAOImpl.TestFields.DIFFICULTY.FIELD)))
                        .startingTime(resultSet.getTimestamp(UserTestFields.STARTING_TIME.FIELD))
                        .endingTime(resultSet.getTimestamp(UserTestFields.ENDING_TIME.FIELD))
                        .result(resultSet.getFloat(UserTestFields.RESULT.FIELD))
                        .build());
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return userTestsInfo;
    }

    /**
     * Select TestInfo of  concrete User and Test
     * @param userId for identification
     * @param testId for identification
     * @return valid entity if it exists, else null.
     */
    @Override
    public TestInfo getTestInfo(long userId, long testId) {
        TestInfo userTestInfo = null;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UserTestQueries.GET_TEST_INFO.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, testId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userTestInfo = new TestInfo.TestInfoBuilder()
                        .userId(resultSet.getLong(UserTestFields.USER_ID.FIELD))
                        .testId(resultSet.getLong(UserTestFields.TEST_ID.FIELD))
                        .testName(resultSet.getString(TestDAOImpl.TestFields.NAME.FIELD))
                        .testSubject(resultSet.getString(TestDAOImpl.TestFields.SUBJECT.FIELD))
                        .testDifficulty(TestDifficulty.getEnum(resultSet.getInt(TestDAOImpl.TestFields.DIFFICULTY.FIELD)))
                        .startingTime(resultSet.getTimestamp(UserTestFields.STARTING_TIME.FIELD))
                        .endingTime(resultSet.getTimestamp(UserTestFields.ENDING_TIME.FIELD))
                        .result(resultSet.getFloat(UserTestFields.RESULT.FIELD))
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return userTestInfo;
    }

    /**
     * Select Test's status
     * @param userId for identification
     * @param testId for identification
     * @return valid status if record exist, else null
     */
    @Override
    public TestStatus getStatus(long userId, long testId) {
        TestStatus testStatus = null;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UserTestQueries.GET_STATUS.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, testId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                testStatus = TestStatus.getEnum(resultSet.getString(UserTestFields.STATUS.FIELD));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return testStatus;
    }

    /**
     * Update Test status for User
     * @param userId for identification
     * @param testId for identification
     * @param status new status
     * @return true if update success, else false
     */
    @Override
    public boolean updateStatus(long userId, long testId, TestStatus status) {
        boolean result = false;
        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UserTestQueries.UPDATE_STATUS.QUERY)) {
            statement.setString(1, status.getValue());
            statement.setLong(2, userId);
            statement.setLong(3, testId);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    /**
     * Select Test starting time for User
     * @param userId for identification
     * @param testId for identification
     * @return valid time if record exist, else null
     */
    @Override
    public Timestamp getStartingTime(long userId, long testId) {
        Timestamp startingTime = null;
        try(Connection connection = datasource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UserTestQueries.GET_STARTING_TIME.QUERY)) {
            statement.setLong(1, userId);
            statement.setLong(2, testId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                startingTime = resultSet.getTimestamp(UserTestFields.STARTING_TIME.FIELD);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return startingTime;
    }

    /**
     * Contains all used queries for user_test table
     */
    enum UserTestQueries {
        GET_TESTS_BY_USER_ID("SELECT name, subject, difficulty, duration," +
                "number_of_questions FROM user_test JOIN test ON user_test.test_id = test.id WHERE user_id = ?"),
        CREATE("INSERT INTO user_test(user_id, test_id, starting_time) VALUES(?, ?, ?)"),
        GET_TESTS_INFO("SELECT * FROM user_test JOIN test ON test_id = test.id " +
                "WHERE user_id = ? ORDER BY starting_time DESC LIMIT ? OFFSET ?"),
        GET_TEST_INFO("SELECT * FROM user_test JOIN test ON test_id = test.id " +
                "WHERE user_id = ? AND test_id = ?"),
        ADD_RESULT_AND_ENDING_TIME("UPDATE user_test SET result = ?, ending_time = ? " +
                "WHERE user_id = ? AND test_id = ?"),
        GET_AMOUNT_OF_RECORDS("SELECT COUNT(user_id) FROM user_test WHERE user_id = ?"),
        GET_STATUS("SELECT status FROM user_test WHERE user_id = ? AND test_id = ?"),
        UPDATE_STATUS("UPDATE user_test SET status = ? WHERE user_id = ? AND test_id = ?"),
        GET_STARTING_TIME("SELECT starting_time FROM user_test WHERE user_id = ? AND test_id = ?");

        final String QUERY;
        UserTestQueries(String query) {
            this.QUERY = query;
        }
    }

    /**
     * Contains all fields in user_test table
     */
    enum UserTestFields {
        USER_ID("user_id"),
        TEST_ID("test_id"),
        STARTING_TIME("starting_time"),
        ENDING_TIME("ending_time"),
        RESULT("result"),
        STATUS("status");

        final String FIELD;
        UserTestFields(String field) {
            this.FIELD = field;
        }
    }
}





