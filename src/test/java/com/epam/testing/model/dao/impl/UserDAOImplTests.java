package com.epam.testing.model.dao.impl;

import com.epam.testing.model.connection.DataSource;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.entity.user.UserRole;
import com.epam.testing.model.entity.user.UserStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static com.epam.testing.model.dao.impl.UserDAOImpl.UserFields.*;

import java.io.InputStream;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserDAOImplTests {
    private final static Integer LIMIT = 3;
    private final static Integer OFFSET = 3;
    private final static Long USER_ID = 14L;
    private final static Long INVALID_ID = -1L;
    private static User userExample;

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
        userExample = new User.UserBuilder()
                .login("login")
                .name("name")
                .surname("surname")
                .email("email")
                .password("password")
                .role(UserRole.CLIENT)
                .status(UserStatus.ACTIVE)
                .build();
        userExample.setId(USER_ID);
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
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(1)).thenReturn(5678);
        assertEquals(5678, userDAO.getAmountOfRecords());

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(0, userDAO.getAmountOfRecords());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertEquals(0, userDAO.getAmountOfRecords());
    }

    @Test
    void getAllMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertTrue(userDAO.getAll(LIMIT, OFFSET).isEmpty());

        makeResultSetReturnValidUser();
        List<User> users = userDAO.getAll(LIMIT, OFFSET);
        assertEquals(1, users.size());
        assertEquals(users.get(0), userExample);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertTrue(userDAO.getAll(LIMIT, OFFSET).isEmpty());
    }

    @Test
    void getByIdMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userDAO.getById(USER_ID));

        makeResultSetReturnValidUser();
        assertEquals(userExample, userDAO.getById(USER_ID));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userDAO.getById(USER_ID));
    }

    @Test
    void createMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertEquals(INVALID_ID, userDAO.create(userExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertEquals(INVALID_ID, userDAO.create(userExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getLong(anyInt())).thenReturn(USER_ID);
        assertEquals(USER_ID, userDAO.create(userExample));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertEquals(INVALID_ID, userDAO.create(userExample));
    }

    @Test
    void updateMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userDAO.update(userExample));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(userDAO.update(userExample));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userDAO.update(userExample));
    }

    @Test
    void updatePasswordTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userDAO.updatePassword(userExample.getPassword(), USER_ID));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(userDAO.updatePassword(userExample.getPassword(), USER_ID));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userDAO.updatePassword(userExample.getPassword(), USER_ID));
    }

    @Test
    void updateAvatarTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);
        InputStream inputStream = null;

        when(mockPreparedStatement.executeUpdate()).thenReturn(-1);
        assertFalse(userDAO.updateAvatar(inputStream, USER_ID));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        assertTrue(userDAO.updateAvatar(inputStream, USER_ID));

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        assertFalse(userDAO.updateAvatar(inputStream, USER_ID));
    }

    @Test
    void deleteMethodTest() {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        assertThrows(UnsupportedOperationException.class, () -> userDAO.delete(USER_ID));
    }

    @Test
    void getByLoginAndPasswordMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        makeResultSetReturnValidUser();
        assertEquals(userExample, userDAO.getByLoginAndPassword(userExample.getLogin(),
                userExample.getPassword()));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userDAO.getByLoginAndPassword(userExample.getLogin(),
                userExample.getPassword()));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userDAO.getByLoginAndPassword(userExample.getLogin(),
                userExample.getPassword()));
    }

    @Test
    void getByLoginMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        makeResultSetReturnValidUser();
        assertEquals(userExample, userDAO.getByLogin(userExample.getLogin()));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userDAO.getByLogin(userExample.getLogin()));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userDAO.getByLogin(userExample.getLogin()));
    }

    @Test
    void getByEmailMethodTest() throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl(mockDataSource);

        makeResultSetReturnValidUser();
        assertEquals(userExample, userDAO.getByEmail(userExample.getEmail()));

        when(mockResultSet.next()).thenReturn(Boolean.FALSE);
        assertNull(userDAO.getByEmail(userExample.getEmail()));

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        assertNull(userDAO.getByEmail(userExample.getEmail()));
    }

    private void makeResultSetReturnValidUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(LOGIN.FIELD)).thenReturn(userExample.getLogin());
        when(mockResultSet.getString(PASSWORD.FIELD)).thenReturn(userExample.getPassword());
        when(mockResultSet.getString(EMAIL.FIELD)).thenReturn(userExample.getEmail());
        when(mockResultSet.getString(NAME.FIELD)).thenReturn(userExample.getName());
        when(mockResultSet.getString(SURNAME.FIELD)).thenReturn(userExample.getSurname());
        when(mockResultSet.getString(STATUS.FIELD)).thenReturn(userExample.getStatus().getName());
        when(mockResultSet.getString(ROLE.FIELD)).thenReturn(userExample.getRole().getName());
        when(mockResultSet.getLong(ID.FIELD)).thenReturn(USER_ID);
    }
}
