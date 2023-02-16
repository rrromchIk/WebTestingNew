package com.epam.testing.model.entity.user;

/**
 * User status enum
 *
 * @author rom4ik
 */

public enum UserStatus {
    ACTIVE("active"),
    BLOCKED("blocked");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }

    public static UserStatus getStatus(String status) {
        return status.equals("active") ? ACTIVE : BLOCKED;
    }

    public String getName() {
        return name;
    }
}
