/* @test    @summary Test for LocalDateTime.plusSeconds(long) method to observe behavior and exception paths    @run main LocalDateTimeWitupTest */

import java.time.LocalDateTime;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsOverflow();
    }

    static void testPlusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusSeconds(0);
        if (!result.equals(dt)) {
            throw new AssertionError("plusSeconds(0) should return the same instance");
        }
    }

    static void testPlusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusSeconds(60);
        LocalDateTime expected = LocalDateTime.of(2023, 3, 15, 12, 1);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(60) did not produce expected result");
        }
    }

    static void testPlusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, 3, 15, 12, 0);
        LocalDateTime result = dt.plusSeconds(-60);
        LocalDateTime expected = LocalDateTime.of(2023, 3, 15, 11, 59);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(-60) did not produce expected result");
        }
    }

    static void testPlusSecondsOverflow() {
        try {
            LocalDateTime dt = LocalDateTime.of(999999999, 12, 31, 23, 59, 59);
            dt.plusSeconds(Long.MAX_VALUE);
            throw new AssertionError("Expected DateTimeException for overflow not thrown");
        } catch (DateTimeException e) {
            // Expected exception
        }
        try {
            LocalDateTime dt = LocalDateTime.of(-999999999, 1, 1, 0, 0);
            dt.plusSeconds(Long.MIN_VALUE);
            throw new AssertionError("Expected DateTimeException for underflow not thrown");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
