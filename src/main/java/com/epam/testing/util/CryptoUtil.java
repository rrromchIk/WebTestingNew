package com.epam.testing.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Crypto util. Generates tokens and hashes them.
 *
 * @author rom4ik
 */
public class CryptoUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private CryptoUtil() {}

    /**
     * Generates random token
     * @return String representation
     */
    public static String generateToken() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    /**
     * @param token for hashing
     * @return String that represent hashed token
     */
    public static String getHashedToken(String token) {
        //sha256hex
        return DigestUtils.sha256Hex(token);
    }
}
