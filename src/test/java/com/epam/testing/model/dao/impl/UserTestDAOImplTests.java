package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.entity.test.TestDifficulty;
import com.epam.testing.model.entity.test.TestInfo;
import com.epam.testing.model.entity.test.TestStatus;
import static com.epam.testing.model.dao.impl.UserTestDAOImpl.UserTestFields.*;
import static com.epam.testing.model.dao.impl.TestDAOImpl.TestFields.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserTestDAOImplTests {
    private final static Integer LIMIT = 3;
    private final static Integer OFFSET = 3;
    private final static Long USER_ID = 14L;
    private final static Long TEST_ID = 15L;
    private final static Timestamp STARTING_TIME = Timestamp.valueOf(LocalDateTime.now());
    private final static Timestamp ENDING_TIME = Timestamp.valueOf(LocalDateTime.now());
    private final static TestStatus TEST_STATUS = TestStatus.NOT_STARTED;
    private final static Float RESULT = 10f;
    private static TestInfo testInfoExample;

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;

    @BeforeAll
    static void setUp() {
        testInfoExample = new TestInfo.TestInfoBuilder()
                .userId(USER_ID)
                .testId(TEST_ID)
                .testName("name")
                .testSubject("Math")
                .testDifficulty(TestDifficulty.HARD)
                .testStatus(TEST_STATUS)
                .startingTime(STARTING_TIME)
                .endingTime(ENDING_TIME)
                .result(RESULT)
                .build();
    }

    @BeforeEach
    void initEach() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
        doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStatement).setLong(anyInt(), anyLong());
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
    }

    @Test
    void getAmountOfRecordsMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(5678);
        assertEquals(5678, userTestDAO.getAmountOfRecords(USER_ID));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(0, userTestDAO.getAmountOfRecords(USER_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertEquals(0, userTestDAO.getAmountOfRecords(USER_ID));
    }

    @Test
    void createMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userTestDAO.create(USER_ID, TEST_ID, STARTING_TIME));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(USER_ID);
        assertTrue(userTestDAO.create(USER_ID, TEST_ID, STARTING_TIME));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userTestDAO.create(USER_ID, TEST_ID, STARTING_TIME));
    }

    @Test
    void addResultAndEndingTimeMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userTestDAO.addResultAndEndingTime(USER_ID, TEST_ID, RESULT, ENDING_TIME));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(USER_ID);
        assertTrue(userTestDAO.addResultAndEndingTime(USER_ID, TEST_ID, RESULT, ENDING_TIME));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userTestDAO.addResultAndEndingTime(USER_ID, TEST_ID, RESULT, ENDING_TIME));
    }

    @Test
    void updateStatusMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userTestDAO.updateStatus(USER_ID, TEST_ID, TEST_STATUS));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(USER_ID);
        assertTrue(userTestDAO.updateStatus(USER_ID, TEST_ID, TEST_STATUS));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userTestDAO.updateStatus(USER_ID, TEST_ID, TEST_STATUS));
    }

    @Test
    void getTestInfoMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userTestDAO.getTestInfo(USER_ID, TEST_ID));

        makeResultSetReturnValidTestInfo();
        assertEquals(userTestDAO.getTestInfo(USER_ID, TEST_ID), testInfoExample);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userTestDAO.getTestInfo(USER_ID, TEST_ID));
    }

    @Test
    void getTestsInfoMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(userTestDAO.getTestsInfo(USER_ID, LIMIT, OFFSET).isEmpty());

        makeResultSetReturnValidTestInfo();
        List<TestInfo> testsInfo = userTestDAO.getTestsInfo(USER_ID, LIMIT, OFFSET);
        assertEquals(1, testsInfo.size());
        assertEquals(testsInfo.get(0), testInfoExample);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(userTestDAO.getTestsInfo(USER_ID, LIMIT, OFFSET).isEmpty());
    }

    @Test
    void getStatusMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(STATUS.FIELD)).thenReturn(testInfoExample.getTestStatus().getValue());
        TestStatus testStatus = userTestDAO.getStatus(USER_ID, TEST_ID);
        assertEquals(testStatus.getValue(), testInfoExample.getTestStatus().getValue());

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userTestDAO.getStatus(USER_ID, TEST_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userTestDAO.getStatus(USER_ID, TEST_ID));
    }

    @Test
    void getStartingTimeMethodTest() throws SQLException {
        UserTestDAOImpl userTestDAO = new UserTestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getTimestamp(UserTestDAOImpl.UserTestFields.STARTING_TIME.FIELD))
                .thenReturn(STARTING_TIME);
        Timestamp startingTime = userTestDAO.getStartingTime(USER_ID, TEST_ID);
        assertEquals(STARTING_TIME, startingTime);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userTestDAO.getStartingTime(USER_ID, TEST_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userTestDAO.getStartingTime(USER_ID, TEST_ID));
    }

    private void makeResultSetReturnValidTestInfo() throws SQLException {
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(UserTestDAOImpl.UserTestFields.USER_ID.FIELD)).thenReturn(testInfoExample.getUserId());
        when(mockResultSet.getLong(UserTestDAOImpl.UserTestFields.TEST_ID.FIELD)).thenReturn(testInfoExample.getTestId());
        when(mockResultSet.getString(NAME.FIELD)).thenReturn(testInfoExample.getTestName());
        when(mockResultSet.getString(SUBJECT.FIELD)).thenReturn(testInfoExample.getTestSubject());
        when(mockResultSet.getInt(DIFFICULTY.FIELD)).thenReturn(testInfoExample.getTestDifficulty().getValue());
        when(mockResultSet.getTimestamp(UserTestDAOImpl.UserTestFields.STARTING_TIME.FIELD)).thenReturn(STARTING_TIME);
        when(mockResultSet.getTimestamp(UserTestDAOImpl.UserTestFields.ENDING_TIME.FIELD)).thenReturn(ENDING_TIME);
        when(mockResultSet.getFloat(UserTestDAOImpl.UserTestFields.RESULT.FIELD)).thenReturn(testInfoExample.getResult());
    }
}
