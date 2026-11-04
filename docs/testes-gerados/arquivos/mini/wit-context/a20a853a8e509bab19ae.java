/*
 * @test
 * @summary Deterministic tests for java.time.LocalDateTime.minusNanos(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusNanosZero();
        testMinusNanosPositive();
        testMinusNanosNegative();
        testMinusNanosOverflowThrows();
        System.out.println("All tests passed.");
    }

    private static void testMinusNanosZero() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime result = dt.minusNanos(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("minusNanos(0) should return the same LocalDateTime instance or equal value");
        }
    }

    private static void testMinusNanosPositive() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 1_000_000);
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 59, 1_000_000 + 999_000_000);
        LocalDateTime result = dt.minusNanos(1_000_000_000L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos with positive nanos did not produce expected result");
        }
    }

    private static void testMinusNanosNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 1_000_000);
        LocalDateTime result = dt.minusNanos(-1_000_000L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos with negative nanos should add nanos");
        }
    }

    private static void testMinusNanosOverflowThrows() {
        // The method minusNanos calls plusWithOverflow which can throw DateTimeException if overflow occurs
        // We test a large nanos value that causes overflow beyond supported LocalDate range
        LocalDateTime dt = LocalDateTime.of(Year.MIN_VALUE + 1, Month.JANUARY, 1, 0, 0);
        long largeNanos = Long.MAX_VALUE;
        try {
            dt.minusNanos(largeNanos);
            // If no exception, test fails
            throw new AssertionError("Expected DateTimeException due to overflow was not thrown");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}