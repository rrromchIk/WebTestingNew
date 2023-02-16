package com.epam.testing.model.entity.user;

import java.sql.Timestamp;

/** UserToken entity class for user_token table
 *
 * @author rom4ik
 */
public class UserToken {
    private final long userId;
    private final String token;
    private final Timestamp expirationDate;

    private UserToken(UserTokenBuilder userTokenBuilder) {
        this.userId = userTokenBuilder.userId;
        this.token = userTokenBuilder.token;
        this.expirationDate = userTokenBuilder.expirationDate;
    }

    public long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public static class UserTokenBuilder {
        private long userId;
        private String token;
        private Timestamp expirationDate;

        public UserTokenBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public UserTokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public UserTokenBuilder expirationDate(Timestamp expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public UserToken build() {
            return new UserToken(this);
        }
    }
}
