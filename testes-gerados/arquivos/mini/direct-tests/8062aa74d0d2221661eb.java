/*
 * @test
 * @summary Test java.time.Duration.dividedBy(long) method
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DurationWitupTest {
    public static void main(String[] args) {
        testDivideByOneReturnsSameInstance();
        testDivideByPositiveNumber();
        testDivideByNegativeNumber();
        testDivideByZeroThrows();
    }

    private static void testDivideByOneReturnsSameInstance() {
        Duration d = Duration.ofSeconds(123, 456);
        Duration result = d.dividedBy(1);
        if (result != d) {
            throw new AssertionError("dividedBy(1) should return the same instance");
        }
    }

    private static void testDivideByPositiveNumber() {
        Duration d = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        Duration result = d.dividedBy(2);
        // Expected: 10.5 / 2 = 5.25 seconds
        long expectedSeconds = 5;
        int expectedNanos = 250_000_000;
        if (result.getSeconds() != expectedSeconds || result.getNano() != expectedNanos) {
            throw new AssertionError("dividedBy(2) failed: expected 5.25 seconds but got " + result);
        }
    }

    private static void testDivideByNegativeNumber() {
        Duration d = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        Duration result = d.dividedBy(-2);
        // Expected: 10.5 / -2 = -5.25 seconds
        long expectedSeconds = -5;
        int expectedNanos = -250_000_000;
        // Duration normalizes nanos to positive, so adjust expected accordingly
        long actualSeconds = result.getSeconds();
        int actualNanos = result.getNano();
        // Normalize expected nanos and seconds
        long normSeconds = expectedSeconds;
        int normNanos = expectedNanos;
        if (normNanos < 0) {
            normNanos += 1_000_000_000;
            normSeconds -= 1;
        }
        if (actualSeconds != normSeconds || actualNanos != normNanos) {
            throw new AssertionError("dividedBy(-2) failed: expected " + normSeconds + "s " + normNanos + "ns but got " + actualSeconds + "s " + actualNanos + "ns");
        }
    }

    private static void testDivideByZeroThrows() {
        Duration d = Duration.ofSeconds(1);
        try {
            d.dividedBy(0);
            throw new AssertionError("dividedBy(0) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("zero")) {
                throw new AssertionError("ArithmeticException message should mention zero division");
            }
        }
    }
}
