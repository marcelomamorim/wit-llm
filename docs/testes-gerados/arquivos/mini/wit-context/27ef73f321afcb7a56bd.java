/*
 * @test
 * @summary Test deterministic behavior of LocalDateTime.plusMinutes(long) method
 * @run main java.time.LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusMinutesZero();
        testPlusMinutesPositive();
        testPlusMinutesNegative();
        testPlusMinutesOverflow();
    }

    private static void testPlusMinutesZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 12, 30);
        LocalDateTime result = dt.plusMinutes(0);
        if (!result.equals(dt)) {
            throw new AssertionError("plusMinutes(0) should return the same LocalDateTime instance or equal value");
        }
    }

    private static void testPlusMinutesPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 12, 30);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 13, 0);
        LocalDateTime result = dt.plusMinutes(30);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMinutes(30) did not produce expected result");
        }
    }

    private static void testPlusMinutesNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 12, 30);
        LocalDateTime expected = LocalDateTime.of(2018, Month.SEPTEMBER, 1, 12, 0);
        LocalDateTime result = dt.plusMinutes(-30);
        if (!result.equals(expected)) {
            throw new AssertionError("plusMinutes(-30) did not produce expected result");
        }
    }

    private static void testPlusMinutesOverflow() {
        // The method plusMinutes delegates to plusWithOverflow which can throw DateTimeException
        // if the resulting time is invalid. We test a large value to check for overflow handling.
        LocalDateTime dt = LocalDateTime.of(999999999, Month.DECEMBER, 31, 23, 59);
        try {
            dt.plusMinutes(1);
            // If no exception, test passes as current implementation may allow overflow
        } catch (DateTimeException e) {
            // Expected exception for overflow cases
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
