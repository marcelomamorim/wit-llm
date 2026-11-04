/*
 * @test
 * @summary Witup-derived tests for LocalDate.datesUntil(LocalDate endExclusive)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testOrderedRange();
        testEqualBoundsGiveEmptyStream();
        testLeapYearBoundary();
        testNegativeYearRange();
        testReversedRangeThrows();
        testReversedRangeNegativeYearThrows();
        testOffByOneReversedRangeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<LocalDate> collect(LocalDate start, LocalDate endExclusive) {
        return start.datesUntil(endExclusive).collect(Collectors.toList());
    }

    static void expectIllegalArgument(LocalDate start, LocalDate endExclusive) {
        try {
            start.datesUntil(endExclusive);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }

    static void testOrderedRange() {
        List<LocalDate> actual = collect(LocalDate.of(2019, 12, 30), LocalDate.of(2020, 1, 2));
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2019, 12, 30),
                LocalDate.of(2019, 12, 31),
                LocalDate.of(2020, 1, 1));
        check(actual.equals(expected), "ordered range mismatch: " + actual);
    }

    static void testEqualBoundsGiveEmptyStream() {
        List<LocalDate> actual = collect(LocalDate.of(2020, 5, 15), LocalDate.of(2020, 5, 15));
        check(actual.isEmpty(), "equal bounds should yield no dates: " + actual);
    }

    static void testLeapYearBoundary() {
        List<LocalDate> actual = collect(LocalDate.of(2020, 2, 28), LocalDate.of(2020, 3, 2));
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(2020, 2, 28),
                LocalDate.of(2020, 2, 29),
                LocalDate.of(2020, 3, 1));
        check(actual.equals(expected), "leap year boundary mismatch: " + actual);
    }

    static void testNegativeYearRange() {
        List<LocalDate> actual = collect(LocalDate.of(-5, 1, 1), LocalDate.of(-5, 1, 3));
        List<LocalDate> expected = Arrays.asList(
                LocalDate.of(-5, 1, 1),
                LocalDate.of(-5, 1, 2));
        check(actual.equals(expected), "negative year range mismatch: " + actual);
    }

    static void testReversedRangeThrows() {
        expectIllegalArgument(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 5));
    }

    static void testReversedRangeNegativeYearThrows() {
        expectIllegalArgument(LocalDate.of(-5, 1, 3), LocalDate.of(-5, 1, 1));
    }

    static void testOffByOneReversedRangeThrows() {
        expectIllegalArgument(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 1));
    }
}
