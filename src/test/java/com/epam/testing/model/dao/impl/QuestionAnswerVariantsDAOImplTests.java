package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class QuestionAnswerVariantsDAOImplTests {
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;
    private static final Long QUESTION_ID = 14L;
    private static final String ANSWER_TEXT = "answer text";

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
    void getAllByQuestionIdMethodTest() throws SQLException {
        QuestionAnswerVariantsDAOImpl questionAnswerVariantsDAO =
                new QuestionAnswerVariantsDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(questionAnswerVariantsDAO.getAllByQuestionId(QUESTION_ID).isEmpty());

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(1)).thenReturn(ANSWER_TEXT);
        List<String> questions = questionAnswerVariantsDAO.getAllByQuestionId(QUESTION_ID);
        assertTrue(questions.size() >= 1);
        assertEquals(ANSWER_TEXT, questions.get(0));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(questionAnswerVariantsDAO.getAllByQuestionId(QUESTION_ID).isEmpty());
    }

    @Test
    void createMethodTest() throws SQLException {
        QuestionAnswerVariantsDAOImpl questionAnswerVariantsDAO=
                new QuestionAnswerVariantsDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(questionAnswerVariantsDAO.create(QUESTION_ID, ANSWER_TEXT));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(questionAnswerVariantsDAO.create(QUESTION_ID, ANSWER_TEXT));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(questionAnswerVariantsDAO.create(QUESTION_ID, ANSWER_TEXT));
    }
}
