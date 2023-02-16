package com.epam.testing.model.service;

import com.epam.testing.model.dao.impl.UserDAOImpl;
import com.epam.testing.model.entity.user.User;
import com.epam.testing.model.entity.user.UserStatus;

import java.io.InputStream;
import java.util.List;

public class UserService {
    private final UserDAOImpl dao;

    public UserService() {
        this.dao = new UserDAOImpl();
    }

    public UserService(UserDAOImpl dao) {
        this.dao = dao;
    }

    public boolean userIsBlocked(String login) {
        User user = dao.getByLogin(login);
        if(user == null) {
            return false;
        }
        return user.getStatus().equals(UserStatus.BLOCKED);
    }

    public int getAmountOfUsers() {
        return dao.getAmountOfRecords();
    }

    public List<User> getAllUsers(int limit, int offset) {
        return dao.getAll(limit, offset);
    }

    public User getUserById(long id) {
        return dao.getById(id);
    }

    public User getUserByLogin(String login) {
        return dao.getByLogin(login);
    }

    public User getUserByEmail(String email) {
        return dao.getByEmail(email);
    }

    public boolean userExists(String login, String password) {
        return dao.getByLoginAndPassword(login, password) != null;
    }

    public boolean addUser(User user) {
        return dao.create(user) != -1;
    }

    public boolean updateUser(User user) {
        return dao.update(user);
    }

    public boolean setAvatar(InputStream img, long userId) {
        return dao.updateAvatar(img, userId);
    }

    public boolean updatePassword(String password, long userId) {
        return dao.updatePassword(password, userId);
    }
}
