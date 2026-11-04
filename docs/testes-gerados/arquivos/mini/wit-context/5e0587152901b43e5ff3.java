/*
 * @test
 * @summary Test java.time.Duration.minusSeconds(long) for normal and edge cases including overflow
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testMinusSecondsNormal();
        testMinusSecondsLongMinValue();
    }

    private static void testMinusSecondsNormal() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusSeconds(5);
        if (!result.equals(Duration.ofSeconds(5))) {
            throw new AssertionError("Expected 5 seconds after minusSeconds(5), got: " + result);
        }

        // Test subtracting zero seconds returns same duration
        result = d.minusSeconds(0);
        if (!result.equals(d)) {
            throw new AssertionError("Expected same duration after minusSeconds(0), got: " + result);
        }

        // Test subtracting negative seconds is equivalent to plusSeconds
        result = d.minusSeconds(-3);
        if (!result.equals(d.plusSeconds(3))) {
            throw new AssertionError("Expected minusSeconds(-3) to equal plusSeconds(3), got: " + result);
        }
    }

    private static void testMinusSecondsLongMinValue() {
        Duration d = Duration.ofSeconds(0);
        // According to source, minusSeconds(Long.MIN_VALUE) calls plusSeconds(Long.MAX_VALUE).plusSeconds(1)
        Duration result = d.minusSeconds(Long.MIN_VALUE);
        Duration expected = d.plusSeconds(Long.MAX_VALUE).plusSeconds(1);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected minusSeconds(Long.MIN_VALUE) to equal plusSeconds(Long.MAX_VALUE).plusSeconds(1), got: " + result);
        }
    }
}
