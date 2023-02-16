package com.epam.testing.controller.command;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * @author rom4ik
 */

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommandFactoryTests {
    @Mock
    HttpServletRequest mockHttpServletRequest;

    @ParameterizedTest
    @MethodSource("getValidCommandNames")
    void getCommandMethodPositiveTest(String action) {
        when(mockHttpServletRequest.getParameter("action")).thenReturn(action);
        Command command = CommandFactory.commandFactory().getCommand(mockHttpServletRequest);
        assertNotNull(command);
    }

    private static Stream<Arguments> getValidCommandNames() {
        return Stream.of(
                Arguments.of("logIn"),
                Arguments.of("signUp"),
                Arguments.of("adminMain"),
                Arguments.of("changeUserStatus"),
                Arguments.of("testInfo"),
                Arguments.of("userMain"),
                Arguments.of("testResult"),
                Arguments.of("resetAvatar")
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidCommandNames")
    void getCommandMethodNegativeTest(String action) {
        when(mockHttpServletRequest.getParameter("action")).thenReturn(action);
        Command command = CommandFactory.commandFactory().getCommand(mockHttpServletRequest);
        assertNull(command);
    }

    private static Stream<Arguments> getInvalidCommandNames() {
        return Stream.of(
                Arguments.of("asd"),
                Arguments.of("assss"),
                Arguments.of("admiaanMain"),
                Arguments.of("changasdaseUserStatus"),
                Arguments.of("testfghInfo"),
                Arguments.of("useadasdrMain"),
                Arguments.of("testasdResult"),
                Arguments.of("reseasdtAvatar")
        );
    }
}
