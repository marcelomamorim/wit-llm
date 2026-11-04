/*
 * @test
 * @summary Test java.time.LocalDateTime.minusNanos(long) method behavior
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testMinusNanosZero();
        testMinusNanosPositive();
        testMinusNanosNegative();
        testMinusNanosCrossDayBoundary();
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
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 59, 999_000_000);
        LocalDateTime result = dt.minusNanos(1_000_000L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(1_000_000) did not produce expected result: " + result);
        }
    }

    private static void testMinusNanosNegative() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 1_000_000);
        LocalDateTime result = dt.minusNanos(-1_000_000L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(-1_000_000) did not produce expected result: " + result);
        }
    }

    private static void testMinusNanosCrossDayBoundary() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0, 0);
        LocalDateTime expected = LocalDateTime.of(2017, Month.DECEMBER, 31, 23, 59, 59, 999_999_999);
        LocalDateTime result = dt.minusNanos(1L);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(1) crossing day boundary did not produce expected result: " + result);
        }
    }
}
