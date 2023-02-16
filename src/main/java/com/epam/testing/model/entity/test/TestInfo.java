package com.epam.testing.model.entity.test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Test info entity for user_test table
 *
 * @author rom4ik
 */

public class TestInfo {
    private final Long userId;
    private final Long testId;
    private final String testName;
    private final String testSubject;
    private final TestDifficulty testDifficulty;
    private final TestStatus testStatus;
    private final Timestamp startingTime;
    private final Timestamp endingTime;
    private Float result;

    private TestInfo(TestInfoBuilder builder) {
        this.userId = builder.userId;
        this.testId = builder.testId;
        this.testName = builder.testName;
        this.testSubject = builder.testSubject;
        this.testDifficulty = builder.testDifficulty;
        this.testStatus = builder.testStatus;
        this.startingTime = builder.startingTime;
        this.endingTime = builder.endingTime;
        this.result = builder.result;
    }

    public TestStatus getTestStatus() {
        return testStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTestId() {
        return testId;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestSubject() {
        return testSubject;
    }

    public TestDifficulty getTestDifficulty() {
        return testDifficulty;
    }

    public String getStartingTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm-dd.MM.yyyy");
        return dateTimeFormatter.format(startingTime.toLocalDateTime());
    }

    public String getEndingTime() {
        LocalDateTime time;
        String endingTimeStr;
        try {
            time = endingTime.toLocalDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm-dd.MM.yyyy");
            endingTimeStr = dateTimeFormatter.format(time);
        } catch (RuntimeException e) {
            endingTimeStr = "not ended";
        }

        return endingTimeStr;
    }


    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TestInfo {" +
                "userId = " + userId +
                ", testId = " + testId +
                ", startingTime = " + startingTime +
                ", endingTime = " + endingTime +
                ", result = " + result + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestInfo testInfo = (TestInfo) o;
        return Objects.equals(userId, testInfo.userId) &&
                Objects.equals(testId, testInfo.testId) &&
                Objects.equals(testName, testInfo.testName) &&
                Objects.equals(testSubject, testInfo.testSubject) &&
                Objects.equals(testDifficulty.getValue(), testInfo.testDifficulty.getValue()) &&
                Objects.equals(startingTime, testInfo.startingTime) &&
                Objects.equals(endingTime, testInfo.endingTime) &&
                Objects.equals(result, testInfo.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, testId, testName, testSubject, testDifficulty, startingTime, endingTime, result);
    }

    /**
     * Builder.
     */
    public static class TestInfoBuilder {
        private Long userId;
        private Long testId;
        private String testName;
        private String testSubject;
        private TestDifficulty testDifficulty;
        private TestStatus testStatus;
        private Timestamp startingTime;
        private Timestamp endingTime;
        private Float result;

        public TestInfoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public TestInfoBuilder testId(Long testId) {
            this.testId = testId;
            return this;
        }

        public TestInfoBuilder startingTime(Timestamp startingTime) {
            this.startingTime = startingTime;
            return this;
        }

        public TestInfoBuilder endingTime(Timestamp endingTime) {
            this.endingTime = endingTime;
            return this;
        }

        public TestInfoBuilder result(Float result) {
            this.result = result;
            return this;
        }

        public TestInfoBuilder testName(String name) {
            this.testName = name;
            return this;
        }

        public TestInfoBuilder testSubject(String testSubject) {
            this.testSubject = testSubject;
            return this;
        }

        public TestInfoBuilder testDifficulty(TestDifficulty testDifficulty) {
            this.testDifficulty = testDifficulty;
            return this;
        }
        public TestInfoBuilder testStatus(TestStatus testStatus) {
            this.testStatus = testStatus;
            return this;
        }


        public TestInfo build() {
            return new TestInfo(this);
        }
    }
}
