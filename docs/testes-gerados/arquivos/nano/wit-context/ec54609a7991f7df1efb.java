/*
 * @test
 * @summary Tests for Duration.plusSeconds(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusSecondsNormal();
        testPlusSecondsZero();
        testPlusSecondsOverflow();
    }

    static void testPlusSecondsNormal() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.plusSeconds(5);
        if (!result.equals(Duration.ofSeconds(15))) {
            throw new AssertionError("Expected 15 seconds, got " + result);
        }
    }

    static void testPlusSecondsZero() {
        Duration d = Duration.ofSeconds(20);
        Duration result = d.plusSeconds(0);
        if (!result.equals(Duration.ofSeconds(20))) {
            throw new AssertionError("Expected 20 seconds, got " + result);
        }
    }

    static void testPlusSecondsOverflow() {
        Duration d = Duration.ofSeconds(Long.MAX_VALUE - 1);
        try {
            d.plusSeconds(2);
            throw new AssertionError("Expected ArithmeticException not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
