/*
 * @test
 * @summary Test java.time.Duration.minusNanos(long) for normal and edge cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testMinusNanosNormal();
        testMinusNanosLongMinValue();
    }

    private static void testMinusNanosNormal() {
        Duration d = Duration.ofSeconds(10, 500);
        long nanosToSubtract = 200;
        Duration result = d.minusNanos(nanosToSubtract);
        // Expected: equivalent to plusNanos(-nanosToSubtract)
        Duration expected = d.plusNanos(-nanosToSubtract);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos normal case failed: expected " + expected + " but got " + result);
        }
    }

    private static void testMinusNanosLongMinValue() {
        Duration d = Duration.ofSeconds(0, 0);
        // minusNanos(Long.MIN_VALUE) triggers special case: plusNanos(Long.MAX_VALUE).plusNanos(1)
        Duration result = d.minusNanos(Long.MIN_VALUE);
        Duration expected = d.plusNanos(Long.MAX_VALUE).plusNanos(1);
        if (!result.equals(expected)) {
            throw new AssertionError("minusNanos Long.MIN_VALUE case failed: expected " + expected + " but got " + result);
        }
    }
}
