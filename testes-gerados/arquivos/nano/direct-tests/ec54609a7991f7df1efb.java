/*
 * @test
 * @summary Test Duration.plusSeconds(long) method for correctness.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testPlusSecondsWithPositiveValue();
        testPlusSecondsWithZero();
        testPlusSecondsWithNegativeValue();
        testPlusSecondsWithLargeValue();
    }

    static void testPlusSecondsWithPositiveValue() {
        Duration original = Duration.ofSeconds(10);
        Duration result = original.plusSeconds(5);
        Duration expected = Duration.ofSeconds(15);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusSeconds with positive value. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusSecondsWithZero() {
        Duration original = Duration.ofSeconds(20);
        Duration result = original.plusSeconds(0);
        if (!result.equals(original)) {
            throw new AssertionError("Failed: plusSeconds with zero. Expected: " + original + ", but got: " + result);
        }
    }

    static void testPlusSecondsWithNegativeValue() {
        Duration original = Duration.ofSeconds(30);
        Duration result = original.plusSeconds(-10);
        Duration expected = Duration.ofSeconds(20);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusSeconds with negative value. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusSecondsWithLargeValue() {
        Duration original = Duration.ofSeconds(1);
        long largeSeconds = Long.MAX_VALUE - 1;
        Duration result = original.plusSeconds(largeSeconds);
        Duration expected = Duration.ofSeconds(largeSeconds + 1);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusSeconds with large value. Expected: " + expected + ", but got: " + result);
        }
    }
}