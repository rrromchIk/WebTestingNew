package com.epam.testing.model.entity.test;

/**
 * Test difficulty enum
 *
 * @author rom4ik
 */

public enum TestDifficulty {
    EASY(0),
    MEDIUM(1),
    HARD(2);

    private final Integer value;

    TestDifficulty(Integer value) {
        this.value = value;
    }

    public static TestDifficulty getEnum(Integer difficulty){
        if (difficulty.equals(0)) return EASY;
        else if (difficulty.equals(1)) return MEDIUM;
        else return HARD;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        String result = "easy";
        if(value == 1) {
            result = "medium";
        } else if(value == 2) {
            result = "hard";
        }
        return result;
    }
}
