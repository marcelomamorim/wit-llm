/*
 * @test
 * @summary Behavioral checks for LocalDateTime.plusMonths, including month-end clamping, zero months and DateTimeException at the year range limits
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testRegularAddition();
        testNegativeAddition();
        testMonthEndClamping();
        testZeroMonthsOnMaxYearDoesNotThrow();
        testYearAboveMaximumThrows();
        testYearBelowMinimumThrows();
    }

    static void testRegularAddition() {
        LocalDateTime start = LocalDateTime.of(2018, 6, 15, 10, 30, 45, 123);
        LocalDateTime expected = LocalDateTime.of(2019, 6, 15, 10, 30, 45, 123);
        check(expected.equals(start.plusMonths(12)), "plusMonths(12) from 2018-06-15 should be 2019-06-15 with time preserved");
    }

    static void testNegativeAddition() {
        LocalDateTime start = LocalDateTime.of(2019, 3, 31, 23, 59, 59, 0);
        LocalDateTime expected = LocalDateTime.of(2019, 2, 28, 23, 59, 59, 0);
        check(expected.equals(start.plusMonths(-1)), "plusMonths(-1) from 2019-03-31 should clamp to 2019-02-28");
    }

    static void testMonthEndClamping() {
        LocalDateTime start = LocalDateTime.of(2019, 1, 31, 8, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2019, 2, 28, 8, 0, 0, 0);
        check(expected.equals(start.plusMonths(1)), "plusMonths(1) from 2019-01-31 should clamp to 2019-02-28");
    }

    static void testZeroMonthsOnMaxYearDoesNotThrow() {
        LocalDateTime start = LocalDateTime.of(999999999, 12, 31, 12, 0, 0, 0);
        LocalDateTime result = start.plusMonths(0);
        check(start.equals(result), "plusMonths(0) at maximum year should return an equal value");
    }

    static void testYearAboveMaximumThrows() {
        final LocalDateTime start = LocalDateTime.of(999999999, 12, 1, 0, 0);
        expectDateTimeException(new Runnable() {
            public void run() {
                start.plusMonths(1);
            }
        }, "plusMonths(1) beyond maximum year should throw DateTimeException");
    }

    static void testYearBelowMinimumThrows() {
        final LocalDateTime start = LocalDateTime.of(-999999999, 1, 1, 0, 0);
        expectDateTimeException(new Runnable() {
            public void run() {
                start.plusMonths(-1);
            }
        }, "plusMonths(-1) beyond minimum year should throw DateTimeException");
    }

    static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
