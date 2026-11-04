/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.minusSeconds(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusSecondsZero();
        testMinusSecondsPositive();
        testMinusSecondsNegative();
        testMinusSecondsOverflow();
    }

    private static void testMinusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 12, 0, 0);
        LocalDateTime result = dt.minusSeconds(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("minusSeconds(0) should return the same LocalDateTime");
        }
    }

    private static void testMinusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0, 10);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0, 0);
        LocalDateTime result = dt.minusSeconds(10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(10) did not subtract seconds correctly");
        }
    }

    private static void testMinusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0, 10);
        LocalDateTime result = dt.minusSeconds(-10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(-10) should add seconds correctly");
        }
    }

    private static void testMinusSecondsOverflow() {
        // Test a large subtraction that causes date to go back by one day
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.AUGUST, 31, 23, 59, 50);
        LocalDateTime result = dt.minusSeconds(10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(10) near day boundary failed");
        }

        // Test large negative seconds that cause date to go forward by one day
        dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 23, 59, 50);
        expected = LocalDateTime.of(2018, Month.SEPTEMBER, 2, 0, 0, 0);
        result = dt.minusSeconds(-10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(-10) near day boundary failed");
        }
    }
}
