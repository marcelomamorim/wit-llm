/*
 * @test
 * @summary Tests for Duration.plusNanos(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusNanosZero();
        testPlusNanosPositive();
        testPlusNanosNegative();
        testPlusNanosLarge();
        testPlusNanosOverflow();
    }

    static void testPlusNanosZero() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.plusNanos(0);
        if (!result.equals(d)) {
            throw new AssertionError("plusNanos(0) should return the same duration");
        }
    }

    static void testPlusNanosPositive() {
        Duration d = Duration.ofSeconds(5);
        Duration result = d.plusNanos(1000000000L); // 1 second in nanos
        Duration expected = Duration.ofSeconds(6);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(1_000_000_000) should add 1 second");
        }
    }

    static void testPlusNanosNegative() {
        Duration d = Duration.ofSeconds(5);
        Duration result = d.plusNanos(-1000000000L); // subtract 1 second
        Duration expected = Duration.ofSeconds(4);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(-1_000_000_000) should subtract 1 second");
        }
    }

    static void testPlusNanosLarge() {
        Duration d = Duration.ofSeconds(1);
        long nanosToAdd = Long.MAX_VALUE;
        Duration result = d.plusNanos(nanosToAdd);
        Duration expected = Duration.ofSeconds(1).plusNanos(nanosToAdd);
        if (!result.equals(expected)) {
            throw new AssertionError("plusNanos(Long.MAX_VALUE) should produce expected duration");
        }
    }

    static void testPlusNanosOverflow() {
        Duration d = Duration.ofSeconds(Long.MAX_VALUE, 999999999);
        try {
            d.plusNanos(1);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
