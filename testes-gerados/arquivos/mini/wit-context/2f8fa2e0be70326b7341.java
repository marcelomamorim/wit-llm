/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.plusSeconds(long)
 * @run main java.time.LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsOverflow();
    }

    private static void testPlusSecondsZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        LocalDateTime result = dt.plusSeconds(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("plusSeconds(0) should return the same LocalDateTime instance or equal value");
        }
    }

    private static void testPlusSecondsPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 1);
        LocalDateTime result = dt.plusSeconds(1L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(1) should add one second correctly");
        }
    }

    private static void testPlusSecondsNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 1);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime result = dt.plusSeconds(-1L);
        if (!result.equals(expected)) {
            throw new AssertionError("plusSeconds(-1) should subtract one second correctly");
        }
    }

    private static void testPlusSecondsOverflow() {
        // The method plusSeconds calls plusWithOverflow which may throw DateTimeException
        // if the resulting LocalDateTime is out of supported range.
        // We test a large positive value that should cause overflow.
        LocalDateTime dt = LocalDateTime.of(999999999, Month.DECEMBER, 31, 23, 59, 59);
        try {
            dt.plusSeconds(10L);
            throw new AssertionError("plusSeconds with overflow did not throw DateTimeException");
        } catch (DateTimeException e) {
            // Expected exception
        }

        // Similarly test large negative value causing underflow
        dt = LocalDateTime.of(-999999999, Month.JANUARY, 1, 0, 0, 0);
        try {
            dt.plusSeconds(-10L);
            throw new AssertionError("plusSeconds with underflow did not throw DateTimeException");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
