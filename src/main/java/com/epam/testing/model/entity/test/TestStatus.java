package com.epam.testing.model.entity.test;

/**
 * Test status enum
 *
 * @author rom4ik
 */

public enum TestStatus {
    NOT_STARTED("not_started"),
    STARTED("started"),
    PASSED("passed");

    private final String value;

    TestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TestStatus getEnum(String value) {
        TestStatus result;
        if(value.equals("not_started")) {
            result = NOT_STARTED;
        } else if(value.equals("started")) {
            result = STARTED;
        } else {
            result = PASSED;
        }

        return result;
    }
}
