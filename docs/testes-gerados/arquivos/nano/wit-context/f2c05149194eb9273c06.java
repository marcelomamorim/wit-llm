/*
 * @test
 * @summary Test for Duration.minusNanos(long)
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testMinusNanosNormal();
        testMinusNanosEdgeCase();
        testMinusNanosOverflow();
    }

    private static void testMinusNanosNormal() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusNanos(5000000000L); // 5 seconds
        if (!result.equals(Duration.ofSeconds(5))) {
            throw new AssertionError("Expected 5 seconds, but got: " + result);
        }
    }

    private static void testMinusNanosEdgeCase() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusNanos(Long.MIN_VALUE);
        Duration expected = d.plusNanos(Long.MAX_VALUE).plusNanos(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Edge case failed: expected " + expected + " but got: " + result);
        }
    }

    private static void testMinusNanosOverflow() {
        Duration d = Duration.ofSeconds(0);
        try {
            d.minusNanos(Long.MAX_VALUE);
        } catch (ArithmeticException e) {
            return; // Expected exception
        }
        throw new AssertionError("Expected ArithmeticException for overflow case");
    }
}
