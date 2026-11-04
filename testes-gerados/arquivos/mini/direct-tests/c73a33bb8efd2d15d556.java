/*
 * @test
 * @summary Test java.time.Duration plus(Duration) method
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusWithPositiveDuration();
        testPlusWithZeroDuration();
        testPlusWithNegativeDuration();
        testPlusWithMaxValues();
    }

    private static void testPlusWithPositiveDuration() {
        Duration d1 = Duration.ofSeconds(10, 500);
        Duration d2 = Duration.ofSeconds(20, 250);
        Duration result = d1.plus(d2);
        long expectedSeconds = 10 + 20;
        int expectedNanos = 500 + 250;
        if (expectedNanos >= 1_000_000_000) {
            expectedSeconds++;
            expectedNanos -= 1_000_000_000;
        }
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("plus with positive duration failed: expected " + expectedSeconds + "s " + expectedNanos + "ns but got " + result);
        }
    }

    private static void testPlusWithZeroDuration() {
        Duration d1 = Duration.ofSeconds(5, 123456789);
        Duration d2 = Duration.ofSeconds(0, 0);
        Duration result = d1.plus(d2);
        if (!result.equals(d1)) {
            throw new AssertionError("plus with zero duration failed: expected " + d1 + " but got " + result);
        }
    }

    private static void testPlusWithNegativeDuration() {
        Duration d1 = Duration.ofSeconds(10, 500);
        Duration d2 = Duration.ofSeconds(-5, -250);
        Duration result = d1.plus(d2);
        long expectedSeconds = 10 - 5;
        int expectedNanos = 500 - 250;
        if (expectedNanos < 0) {
            expectedSeconds--;
            expectedNanos += 1_000_000_000;
        }
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("plus with negative duration failed: expected " + expectedSeconds + "s " + expectedNanos + "ns but got " + result);
        }
    }

    private static void testPlusWithMaxValues() {
        Duration d1 = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        Duration d2 = Duration.ofSeconds(0, 1);
        try {
            d1.plus(d2);
            throw new AssertionError("Expected ArithmeticException due to overflow not thrown");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
