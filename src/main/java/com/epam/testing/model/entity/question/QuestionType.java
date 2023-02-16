package com.epam.testing.model.entity.question;

/**
 * Question type enum
 *
 * @author rom4ik
 */
public enum QuestionType {
    MULTIPLE_CORRECT_ANSWERS("multiple_answers"),
    SINGLE_CORRECT_ANSWER("single_answer");

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionType getEnum(String value) {
        if(value.equals("multiple_answers")) {
            return MULTIPLE_CORRECT_ANSWERS;
        } else {
            return SINGLE_CORRECT_ANSWER;
        }
    }
}
