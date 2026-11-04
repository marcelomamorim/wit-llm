/*
 * @test
 * @summary Test java.time.Duration plusNanos method
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusNanosZero();
        testPlusNanosPositive();
        testPlusNanosNegative();
        testPlusNanosOverflow();
    }

    private static void testPlusNanosZero() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration result = d.plusNanos(0);
        if (!result.equals(d)) {
            throw new AssertionError("plusNanos(0) should return equal Duration");
        }
    }

    private static void testPlusNanosPositive() {
        Duration d = Duration.ofSeconds(10, 500);
        Duration result = d.plusNanos(1500);
        Duration expected = Duration.ofSeconds(10, 2000);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos with positive nanosToAdd failed");
        }
    }

    private static void testPlusNanosNegative() {
        Duration d = Duration.ofSeconds(10, 1500);
        Duration result = d.plusNanos(-1000);
        Duration expected = Duration.ofSeconds(10, 500);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos with negative nanosToAdd failed");
        }
    }

    private static void testPlusNanosOverflow() {
        Duration d = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        try {
            d.plusNanos(1);
            throw new AssertionError("Expected ArithmeticException due to overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
