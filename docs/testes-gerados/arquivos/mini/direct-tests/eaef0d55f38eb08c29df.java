/*
 * @test
 * @summary Test java.time.LocalDateTime.minusSeconds(long) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusSecondsZero();
        testMinusSecondsPositive();
        testMinusSecondsNegative();
        testMinusSecondsOverflowDay();
    }

    private static void testMinusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 0, 0);
        LocalDateTime result = dt.minusSeconds(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("minusSeconds(0) should return the same date-time");
        }
    }

    private static void testMinusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 10);
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 55);
        LocalDateTime result = dt.minusSeconds(15L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(15) did not subtract seconds correctly: got " + result);
        }
    }

    private static void testMinusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 10);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 20);
        LocalDateTime result = dt.minusSeconds(-10L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(-10) should add 10 seconds: got " + result);
        }
    }

    private static void testMinusSecondsOverflowDay() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 59);
        LocalDateTime result = dt.minusSeconds(1L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusSeconds(1) should handle day overflow: got " + result);
        }
    }
}
