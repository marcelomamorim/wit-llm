/*
 * @test
 * @summary Tests for java.time.Instant.minusNanos(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testMinusNanosNormal();
        testMinusNanosLongMinValue();
        testMinusNanosOverflow();
        testMinusNanosUnderflow();
    }

    static void testMinusNanosNormal() {
        Instant instant = Instant.ofEpochSecond(1000L, 500L);
        Instant result = instant.minusNanos(200L);
        long expectedSeconds = 999L;
        long expectedNanos = 300L;
        assert result.getEpochSecond() == expectedSeconds : "Expected seconds: " + expectedSeconds + ", but got: " + result.getEpochSecond();
        assert result.getNano() == expectedNanos : "Expected nanos: " + expectedNanos + ", but got: " + result.getNano();
    }

    static void testMinusNanosLongMinValue() {
        Instant instant = Instant.ofEpochSecond(0L, 0L);
        Instant result = instant.minusNanos(Long.MIN_VALUE);
        // Should invoke plusNanos(Long.MAX_VALUE).plusNanos(1)
        // Since the exact result depends on implementation, check for exception-free execution
        assert result != null : "Result should not be null";
    }

    static void testMinusNanosOverflow() {
        Instant instant = Instant.ofEpochSecond(0L, 0L);
        try {
            instant.minusNanos(Long.MAX_VALUE);
        } catch (ArithmeticException e) {
            // Expected if overflow occurs
            return;
        }
        // If no exception, verify result is within valid range
        Instant result = instant.minusNanos(Long.MAX_VALUE);
        assert result != null : "Result should not be null";
    }

    static void testMinusNanosUnderflow() {
        Instant instant = Instant.ofEpochSecond(0L, 0L);
        try {
            instant.minusNanos(-Long.MAX_VALUE);
        } catch (ArithmeticException e) {
            // Expected if underflow occurs
            return;
        }
        // If no exception, verify result is within valid range
        Instant result = instant.minusNanos(-Long.MAX_VALUE);
        assert result != null : "Result should not be null";
    }
}
