package com.epam.testing.model.dao;

import com.epam.testing.model.entity.user.UserToken;

/** UserToken DAO interface
 *
 * @author rom4ik
 */
public interface UserTokenDAO {
    boolean create(UserToken userToken);
    UserToken read(String token);
}
