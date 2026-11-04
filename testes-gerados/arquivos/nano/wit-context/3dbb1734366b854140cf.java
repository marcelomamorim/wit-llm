/*
 * @test
 * @summary Test for LocalDateTime.plusNanos(long nanos)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusNanosWithZero();
        testPlusNanosWithPositive();
        testPlusNanosWithNegative();
        testPlusNanosWithLargeValue();
    }

    static void testPlusNanosWithZero() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dt.plusNanos(0L);
        if (!result.equals(dt)) {
            throw new AssertionError("plusNanos(0) should return the same LocalDateTime");
        }
    }

    static void testPlusNanosWithPositive() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        long nanosToAdd = 5000000000L; // 5 seconds
        LocalDateTime result = dt.plusNanos(nanosToAdd);
        LocalDateTime expected = dt.plusSeconds(5);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(5_000_000_000) should add 5 seconds");
        }
    }

    static void testPlusNanosWithNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        long nanosToSubtract = -2000000000L; // -2 seconds
        LocalDateTime result = dt.plusNanos(nanosToSubtract);
        LocalDateTime expected = dt.minusSeconds(2);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(-2_000_000_000) should subtract 2 seconds");
        }
    }

    static void testPlusNanosWithLargeValue() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        long nanosToAdd = Long.MAX_VALUE;
        try {
            LocalDateTime result = dt.plusNanos(nanosToAdd);
            // Result is expected to be a valid LocalDateTime, no exception thrown
        } catch (Exception e) {
            throw new AssertionError("plusNanos(Long.MAX_VALUE) should not throw exception", e);
        }
    }
}
