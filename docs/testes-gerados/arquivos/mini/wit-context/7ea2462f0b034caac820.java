/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.plusHours(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusHoursZero();
        testPlusHoursPositive();
        testPlusHoursNegative();
        testPlusHoursOverflow();
    }

    private static void testPlusHoursZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 10, 30);
        LocalDateTime result = dt.plusHours(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("plusHours(0) should return the same LocalDateTime instance or equal value");
        }
    }

    private static void testPlusHoursPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 10, 30);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 15, 30);
        LocalDateTime result = dt.plusHours(5L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusHours(5) did not produce expected result");
        }
    }

    private static void testPlusHoursNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 10, 30);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 5, 30);
        LocalDateTime result = dt.plusHours(-5L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusHours(-5) did not produce expected result");
        }
    }

    private static void testPlusHoursOverflow() {
        // The plusHours method internally calls plusWithOverflow which may throw DateTimeException
        // if the resulting time is invalid (e.g. nanosecond overflow beyond valid range).
        // We test a large value that causes overflow and expect DateTimeException.
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 0, 0);
        long largeHours = 1000000000000L; // very large to cause overflow
        try {
            dt.plusHours(largeHours);
            throw new AssertionError("Expected DateTimeException not thrown for large plusHours");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
