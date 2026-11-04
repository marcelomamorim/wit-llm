/*
 * @test
 * @summary Test java.time.Duration.plus(Duration) for normal and overflow cases
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusNormal();
        testPlusZeroDuration();
        testPlusOverflow();
    }

    private static void testPlusNormal() {
        Duration d1 = Duration.ofSeconds(10, 500);
        Duration d2 = Duration.ofSeconds(20, 250);
        Duration result = d1.plus(d2);
        long expectedSeconds = 30;
        int expectedNanos = 750;
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("plus normal failed: expected " + expectedSeconds + "s " + expectedNanos + "ns but got " + result);
        }
    }

    private static void testPlusZeroDuration() {
        Duration d1 = Duration.ofSeconds(5, 123);
        Duration zero = Duration.ofSeconds(0, 0);
        Duration result = d1.plus(zero);
        if (result != d1) {
            throw new AssertionError("plus with zero duration should return same duration instance or equal value");
        }
    }

    private static void testPlusOverflow() {
        // This test tries to trigger overflow in plus by adding large seconds
        Duration max = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);
        Duration oneNano = Duration.ofSeconds(0, 1);
        try {
            max.plus(oneNano);
            throw new AssertionError("Expected ArithmeticException due to overflow not thrown");
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
