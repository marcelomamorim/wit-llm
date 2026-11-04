/*
 * @test
 * @summary Test java.time.Duration.toNanos() for normal and overflow cases
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DurationWitupTest {
    public static void main(String[] args) {
        testToNanosNormal();
        testToNanosOverflowPositive();
        testToNanosOverflowNegative();
    }

    private static void testToNanosNormal() {
        // Normal duration within safe range
        Duration d = Duration.of(1, ChronoUnit.SECONDS).plusNanos(500);
        long nanos = d.toNanos();
        if (nanos != 1_000_000_500L) {
            throw new AssertionError("Expected 1000000500 but got " + nanos);
        }
    }

    private static void testToNanosOverflowPositive() {
        // Duration that causes positive overflow in toNanos
        // Use large seconds close to Long.MAX_VALUE / NANOS_PER_SECOND
        long maxSeconds = Long.MAX_VALUE / 1_000_000_000L;
        Duration d = Duration.of(maxSeconds, ChronoUnit.SECONDS).plusNanos(999_999_999);
        try {
            d.toNanos();
            throw new AssertionError("Expected ArithmeticException for positive overflow");
        } catch (ArithmeticException expected) {
            // Expected overflow
        }
    }

    private static void testToNanosOverflowNegative() {
        // Duration that causes negative overflow in toNanos
        // Use seconds close to Long.MIN_VALUE / NANOS_PER_SECOND
        long minSeconds = Long.MIN_VALUE / 1_000_000_000L;
        Duration d = Duration.of(minSeconds, ChronoUnit.SECONDS).minusNanos(1);
        try {
            d.toNanos();
            throw new AssertionError("Expected ArithmeticException for negative overflow");
        } catch (ArithmeticException expected) {
            // Expected overflow
        }
    }
}
