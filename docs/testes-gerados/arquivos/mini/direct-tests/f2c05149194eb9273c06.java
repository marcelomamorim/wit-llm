/*
 * @test
 * @summary Test java.time.Duration.minusNanos(long) method behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testMinusNanosZero();
        testMinusNanosPositive();
        testMinusNanosNegative();
        testMinusNanosLongMinValue();
    }

    private static void testMinusNanosZero() {
        Duration d = Duration.ofSeconds(1, 500);
        Duration result = d.minusNanos(0);
        if (!result.equals(d)) {
            throw new AssertionError("minusNanos(0) should return equal Duration");
        }
    }

    private static void testMinusNanosPositive() {
        Duration d = Duration.ofSeconds(2, 1000);
        long nanosToSubtract = 500;
        Duration expected = d.plusNanos(-nanosToSubtract);
        Duration result = d.minusNanos(nanosToSubtract);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(positive) did not match expected result");
        }
    }

    private static void testMinusNanosNegative() {
        Duration d = Duration.ofSeconds(3, 2000);
        long nanosToSubtract = -1500;
        Duration expected = d.plusNanos(-nanosToSubtract); // plusNanos(1500)
        Duration result = d.minusNanos(nanosToSubtract);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(negative) did not match expected result");
        }
    }

    private static void testMinusNanosLongMinValue() {
        Duration d = Duration.ofSeconds(4, 3000);
        // According to method, minusNanos(Long.MIN_VALUE) == plusNanos(Long.MAX_VALUE).plusNanos(1)
        Duration expected = d.plusNanos(Long.MAX_VALUE).plusNanos(1);
        Duration result = d.minusNanos(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos(Long.MIN_VALUE) did not match expected result");
        }
    }
}
