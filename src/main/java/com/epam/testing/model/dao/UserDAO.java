package com.epam.testing.model.dao;

import com.epam.testing.model.entity.user.User;

import java.io.InputStream;

/** User DAO interface
 *
 * @author rom4ik
 */

public interface UserDAO extends DAO<User> {
    User getByLoginAndPassword(String login, String password);
    User getByLogin(String login);
    User getByEmail(String email);
    boolean updateAvatar(InputStream img, long userId);
    boolean updatePassword(String password, long userId);
}
