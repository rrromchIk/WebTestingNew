package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.entity.question.Question;
import com.epam.testing.model.entity.question.QuestionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;
import java.util.List;

import static com.epam.testing.model.dao.impl.QuestionDAOImpl.QuestionFields.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestionDAOImplTests {
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;
    private final static Long INVALID_ID = -1L;
    private static final Long QUESTION_ID = 14L;
    private static final Long TEST_ID = 15L;
    private static Question questionExample;

    @BeforeAll
    static void setUp() {
        questionExample = new Question.QuestionBuilder()
                .id(QUESTION_ID)
                .type(QuestionType.MULTIPLE_CORRECT_ANSWERS)
                .text("question text")
                .maxScore(2)
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
    void getAmountOfRecordsByTestIdMethodTest() throws SQLException {
        QuestionDAOImpl questionDAO = new QuestionDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(5678);
        assertEquals(5678, questionDAO.getAmountOfRecordsByTestId(TEST_ID));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(0, questionDAO.getAmountOfRecordsByTestId(TEST_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertEquals(0, questionDAO.getAmountOfRecordsByTestId(TEST_ID));
    }

    @Test
    void getAllByTestIdMethodTest() throws SQLException {
        QuestionDAOImpl questionDAO = new QuestionDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(questionDAO.getAllByTestId(TEST_ID).isEmpty());

        makeResultSetReturnValidQuestion();
        List<Question> questions = questionDAO.getAllByTestId(TEST_ID);
        assertTrue(questions.size() >= 1);
        assertEquals(questionExample, questions.get(0));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(questionDAO.getAllByTestId(TEST_ID).isEmpty());
    }

    @Test
    void createMethodTest() throws SQLException {
        QuestionDAOImpl questionDAO = new QuestionDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertEquals(INVALID_ID, questionDAO.create(TEST_ID, questionExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(INVALID_ID, questionDAO.create(TEST_ID, questionExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(QUESTION_ID);
        assertEquals(QUESTION_ID, questionDAO.create(TEST_ID, questionExample));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertEquals(INVALID_ID, questionDAO.create(TEST_ID, questionExample));
    }

    @Test
    void deleteMethodTest() throws SQLException {
        QuestionDAOImpl questionDAO = new QuestionDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(questionDAO.delete(QUESTION_ID));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(questionDAO.delete(QUESTION_ID));
    }

    private void makeResultSetReturnValidQuestion() throws SQLException {
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(TYPE.FIELD)).thenReturn(questionExample.getType().getValue());
        when(mockResultSet.getString(TEXT.FIELD)).thenReturn(questionExample.getText());
        when(mockResultSet.getInt(MAX_SCORE.FIELD)).thenReturn(questionExample.getMaxScore());

        when(mockResultSet.getLong(ID.FIELD)).thenReturn(QUESTION_ID);
    }
}
