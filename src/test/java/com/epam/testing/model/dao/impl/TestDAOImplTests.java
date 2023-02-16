package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.entity.test.TestDifficulty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.testing.model.dao.impl.TestDAOImpl.TestFields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestDAOImplTests {
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;
    private final static Integer LIMIT = 3;
    private final static Integer OFFSET = 3;
    private final static Long INVALID_ID = -1L;
    private static final Long TEST_ID = 14L;
    private static com.epam.testing.model.entity.test.Test testExample;

    @BeforeAll
    static void setUp() {
        testExample = new com.epam.testing.model.entity.test.Test.TestBuilder()
                .name("testName")
                .difficulty(TestDifficulty.HARD)
                .duration(15)
                .numberOfQuestions(10)
                .subject("Math")
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
    void getByNameMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        makeResultSetReturnValidTest();
        assertEquals(testExample, testDAO.getByName(testExample.getName()));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(testDAO.getByName(testExample.getName()));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(testDAO.getByName(testExample.getName()));
    }

    @Test
    void getAmountOfRecordsMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(5678);
        assertEquals(5678, testDAO.getAmountOfRecords());

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(0, testDAO.getAmountOfRecords());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertEquals(0, testDAO.getAmountOfRecords());
    }

    @Test
    void getAmountOnPartSubjectMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(5678);
        assertEquals(5678, testDAO.getAmountOnParticularSubject(testExample.getSubject()));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(0, testDAO.getAmountOnParticularSubject(testExample.getSubject()));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertEquals(0, testDAO.getAmountOnParticularSubject(testExample.getSubject()));
    }

    @Test
    void getByIdMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(testDAO.getById(TEST_ID));

        makeResultSetReturnValidTest();
        assertEquals(testExample, testDAO.getById(TEST_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(testDAO.getById(TEST_ID));
    }

    @Test
    void createMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertEquals(INVALID_ID, testDAO.create(testExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(INVALID_ID, testDAO.create(testExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(TEST_ID);
        assertEquals(TEST_ID, testDAO.create(testExample));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertEquals(INVALID_ID, testDAO.create(testExample));
    }

    @Test
    void updateMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(testDAO.update(testExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(testDAO.update(testExample));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(testDAO.update(testExample));
    }

    @Test
    void deleteMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(testDAO.delete(TEST_ID));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(testDAO.delete(TEST_ID));
    }

    @Test
    void getAllMethodTest() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(testDAO.getAll(LIMIT, OFFSET).isEmpty());

        makeResultSetReturnValidTest();
        List<com.epam.testing.model.entity.test.Test> tests = testDAO.getAll(LIMIT, OFFSET);
        assertTrue(tests.size() >= 1);
        assertEquals(testExample, tests.get(0));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(testDAO.getAll(LIMIT, OFFSET).isEmpty());
    }

    @Test
    void getAllTestsSubjects() throws SQLException {
        TestDAOImpl testDAO = new TestDAOImpl(mockDataSource);
        List<String> subjects = new ArrayList<>();
        subjects.add("Math");

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(testDAO.getAllTestsSubjects().isEmpty());

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(SUBJECT.FIELD)).thenReturn(subjects.get(0));
        assertEquals(subjects, testDAO.getAllTestsSubjects());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(testDAO.getAllTestsSubjects().isEmpty());
    }

    private void makeResultSetReturnValidTest() throws SQLException {
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(NAME.FIELD)).thenReturn(testExample.getName());
        when(mockResultSet.getString(SUBJECT.FIELD)).thenReturn(testExample.getSubject());
        when(mockResultSet.getInt(DURATION.FIELD)).thenReturn(testExample.getDuration());
        when(mockResultSet.getInt(DIFFICULTY.FIELD)).thenReturn(testExample.getDifficulty().getValue());
        when(mockResultSet.getInt(NUMBER_OF_QUESTIONS.FIELD)).thenReturn(testExample.getNumberOfQuestions());

        when(mockResultSet.getLong(ID.FIELD)).thenReturn(TEST_ID);
    }

}
