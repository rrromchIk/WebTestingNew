package com.epam.testing.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class PaginationServiceTests {
    @ParameterizedTest
    @MethodSource("sourceForNumberOfPagesTest")
    void getNumberOfPagesTest(Integer numberOfPages, Integer limit, Integer totalNumbers) {
        assertEquals(numberOfPages, PaginationService.getNumberOfPages(limit, totalNumbers));
    }

    private static Stream<Arguments> sourceForNumberOfPagesTest() {
        return Stream.of(
                Arguments.of(1, 3, 3),
                Arguments.of(2, 3, 4),
                Arguments.of(1, 3, 2),
                Arguments.of(0, 3, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceForOffsetCalcTest")
    void getOffsetOnCertainPageTest(Integer offset, Integer limit, Integer page) {
        assertEquals(offset, PaginationService.getOffsetOnCertainPage(limit, page));
    }

    private static Stream<Arguments> sourceForOffsetCalcTest() {
        return Stream.of(
                Arguments.of(0, 3, 1),
                Arguments.of(6, 3, 3),
                Arguments.of(9, 3, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceForValidPageTest")
    void getValidPageNumberTest(Integer validPage, String page, Integer totalNumber, Integer limit) {
        assertEquals(validPage, PaginationService.getValidPageNumber(page, totalNumber, limit));
    }

    private static Stream<Arguments> sourceForValidPageTest() {
        return Stream.of(
                Arguments.of(1, "0", 4, 3),
                Arguments.of(1, "1", 4, 3),
                Arguments.of(1, "-1", 4, 3),
                Arguments.of(1, "4.5", 4, 3),
                Arguments.of(1, "", 4, 3),
                Arguments.of(1, null, 4, 3),
                Arguments.of(1, "asd", 4, 3),
                Arguments.of(2, "2", 4, 3),
                Arguments.of(1, "2", 3, 3)
        );
    }
}
