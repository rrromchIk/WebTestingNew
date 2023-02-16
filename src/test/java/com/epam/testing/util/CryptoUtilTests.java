package com.epam.testing.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rom4ik
 */
class CryptoUtilTests {

    @ParameterizedTest
    @MethodSource("generateTokens")
    void tokenLengthTest(String generatedToken) {
        assertEquals(20, generatedToken.length());
    }

    private static Stream<Arguments> generateTokens() {
        return Stream.of(
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken()),
                Arguments.of(CryptoUtil.generateToken())
        );
    }

    @ParameterizedTest
    @MethodSource("generateTokens")
    void tokenContainsDigitsTest(String generatedToken) {
        String regex = ".*\\d.*";  // regex to check if string contains any numbers
        Pattern pattern = Pattern.compile(regex);

        // find match between given string and pattern
        Matcher matcherText = pattern.matcher(generatedToken);
        assertTrue(matcherText.matches());
    }

    @ParameterizedTest
    @MethodSource("generateTokens")
    void tokenContainsLettersTest(String generatedToken) {
        String regex = ".*[a-zA-Z].*";  // regex to check if string contains any letters
        Pattern pattern = Pattern.compile(regex);

        // find match between given string and pattern
        Matcher matcherText = pattern.matcher(generatedToken);
        assertTrue(matcherText.matches());
    }

    @ParameterizedTest
    @MethodSource("generateTokens")
    void hashedTokenEqualsTest(String generatedToken) {
        assertEquals(CryptoUtil.getHashedToken(generatedToken),
                CryptoUtil.getHashedToken(generatedToken));
    }
}
