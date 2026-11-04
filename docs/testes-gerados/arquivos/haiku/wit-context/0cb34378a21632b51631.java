/*
 * @test
 * @summary Witup-generated behavioral checks for LocalDate.plusMonths
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testZeroReturnsSameInstance();
        testSimpleAddition();
        testYearRollover();
        testNegativeMonths();
        testEndOfMonthClamping();
        testLeapYearClamping();
        testMaxBoundaryOverflow();
        testMinBoundaryUnderflow();
        testMaxBoundaryZeroIsValid();
    }

    private static void testZeroReturnsSameInstance() {
        LocalDate d = LocalDate.of(2020, 5, 10);
        check(d.plusMonths(0) == d, "plusMonths(0) must return this");
    }

    private static void testSimpleAddition() {
        check(LocalDate.of(2020, 3, 15).plusMonths(2).equals(LocalDate.of(2020, 5, 15)), "simple add failed");
    }

    private static void testYearRollover() {
        check(LocalDate.of(2020, 12, 15).plusMonths(1).equals(LocalDate.of(2021, 1, 15)), "year rollover failed");
        check(LocalDate.of(2020, 1, 15).plusMonths(24).equals(LocalDate.of(2022, 1, 15)), "two-year add failed");
    }

    private static void testNegativeMonths() {
        check(LocalDate.of(2020, 3, 15).plusMonths(-3).equals(LocalDate.of(2019, 12, 15)), "negative months failed");
        check(LocalDate.of(2020, 1, 15).plusMonths(-1).equals(LocalDate.of(2019, 12, 15)), "negative rollover failed");
    }

    private static void testEndOfMonthClamping() {
        check(LocalDate.of(2020, 1, 31).plusMonths(1).equals(LocalDate.of(2020, 2, 29)), "clamp to leap Feb failed");
        check(LocalDate.of(2019, 1, 31).plusMonths(1).equals(LocalDate.of(2019, 2, 28)), "clamp to non-leap Feb failed");
    }

    private static void testLeapYearClamping() {
        check(LocalDate.of(2024, 2, 29).plusMonths(12).equals(LocalDate.of(2025, 2, 28)), "leap day plus 12 months failed");
    }

    private static void testMaxBoundaryOverflow() {
        expectDateTimeException(LocalDate.MAX, 1);
    }

    private static void testMinBoundaryUnderflow() {
        expectDateTimeException(LocalDate.MIN, -1);
    }

    private static void testMaxBoundaryZeroIsValid() {
        check(LocalDate.MAX.plusMonths(0).equals(LocalDate.MAX), "MAX plusMonths(0) failed");
    }

    private static void expectDateTimeException(LocalDate d, long months) {
        try {
            LocalDate r = d.plusMonths(months);
            throw new RuntimeException("expected DateTimeException but got " + r);
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
