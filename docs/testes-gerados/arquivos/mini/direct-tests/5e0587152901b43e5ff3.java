/*
 * @test
 * @summary Test java.time.Duration.minusSeconds(long) method behavior
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {
    public static void main(String[] args) {
        testMinusSecondsPositive();
        testMinusSecondsNegative();
        testMinusSecondsLongMinValue();
    }

    private static void testMinusSecondsPositive() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusSeconds(5);
        if (result.getSeconds() != 5 || result.getNano() != 0) {
            throw new AssertionError("Expected 5 seconds after minusSeconds(5), got " + result);
        }
    }

    private static void testMinusSecondsNegative() {
        Duration d = Duration.ofSeconds(10);
        Duration result = d.minusSeconds(-3);
        if (result.getSeconds() != 13 || result.getNano() != 0) {
            throw new AssertionError("Expected 13 seconds after minusSeconds(-3), got " + result);
        }
    }

    private static void testMinusSecondsLongMinValue() {
        Duration d = Duration.ofSeconds(0);
        Duration result = d.minusSeconds(Long.MIN_VALUE);
        // minusSeconds(Long.MIN_VALUE) == plusSeconds(Long.MAX_VALUE).plusSeconds(1)
        // So result should be Duration.ofSeconds(Long.MAX_VALUE).plusSeconds(1)
        // which equals Duration.ofSeconds(Long.MIN_VALUE) due to overflow in plusSeconds
        // But Duration uses long arithmetic and handles overflow by adding nanos, so test exact value
        long expectedSeconds = Long.MAX_VALUE + 1L; // overflow to Long.MIN_VALUE
        // Because plusSeconds(Long.MAX_VALUE).plusSeconds(1) == plusSeconds(Long.MIN_VALUE)
        // So result should equal d.plusSeconds(Long.MIN_VALUE)
        Duration expected = d.plusSeconds(Long.MIN_VALUE);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result + " for minusSeconds(Long.MIN_VALUE)");
        }
    }
}
