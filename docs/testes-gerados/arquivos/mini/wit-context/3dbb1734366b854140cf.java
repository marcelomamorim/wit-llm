/*
 * @test
 * @summary Test deterministic behavior of LocalDateTime.plusNanos(long) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testPlusNanosZero();
        testPlusNanosPositive();
        testPlusNanosNegative();
        testPlusNanosOverflowThrows();
    }

    private static void testPlusNanosZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0);
        LocalDateTime result = dt.plusNanos(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("plusNanos(0) should return the same LocalDateTime instance or equal value");
        }
    }

    private static void testPlusNanosPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime result = dt.plusNanos(1_000_000_000L); // plus 1 second
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 1, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(1_000_000_000) should add one second");
        }
    }

    private static void testPlusNanosNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 1, 0);
        LocalDateTime result = dt.plusNanos(-1_000_000_000L); // minus 1 second
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(-1_000_000_000) should subtract one second");
        }
    }

    private static void testPlusNanosOverflowThrows() {
        LocalDateTime dt = LocalDateTime.of(999999999, Month.DECEMBER, 31, 23, 59, 59, 999_999_999);
        try {
            // Adding 1 nano should overflow the supported range and throw DateTimeException
            dt.plusNanos(1L);
            throw new AssertionError("plusNanos overflow should throw DateTimeException");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
