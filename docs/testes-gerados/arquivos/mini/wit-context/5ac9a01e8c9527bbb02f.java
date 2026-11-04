/*
 * @test
 * @summary Test java.time.Instant.plusMillis(long) for normal and boundary cases
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.DateTimeException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPlusMillisZero();
        testPlusMillisPositive();
        testPlusMillisNegative();
        testPlusMillisOverflowArithmeticException();
        testPlusMillisOverflowDateTimeException();
        System.out.println("All tests passed.");
    }

    private static void testPlusMillisZero() {
        Instant now = Instant.now();
        Instant result = now.plusMillis(0L);
        if (!result.equals(now)) {
            throw new AssertionError("plusMillis(0) should return the same Instant");
        }
    }

    private static void testPlusMillisPositive() {
        Instant base = Instant.ofEpochSecond(1_000_000_000L, 500_000_000);
        Instant plus = base.plusMillis(1500L); // 1.5 seconds
        Instant expected = base.plusSeconds(1).plusNanos(500_000_000);
        if (!plus.equals(expected)) {
            throw new AssertionError("plusMillis positive failed: expected " + expected + " but got " + plus);
        }
    }

    private static void testPlusMillisNegative() {
        Instant base = Instant.ofEpochSecond(1_000_000_000L, 500_000_000);
        Instant minus = base.plusMillis(-1500L); // -1.5 seconds
        Instant expected = base.minusSeconds(1).minusNanos(500_000_000);
        if (!minus.equals(expected)) {
            throw new AssertionError("plusMillis negative failed: expected " + expected + " but got " + minus);
        }
    }

    private static void testPlusMillisOverflowArithmeticException() {
        // This test tries to trigger ArithmeticException by adding millis that cause long overflow in seconds calculation
        // The exact value is chosen to cause overflow in seconds addition
        long largeMillis = Long.MAX_VALUE / 1000 + 10_000; // large enough to overflow seconds
        Instant base = Instant.ofEpochSecond(0L, 0);
        try {
            base.plusMillis(largeMillis);
            throw new AssertionError("Expected ArithmeticException not thrown for overflow in plusMillis");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    private static void testPlusMillisOverflowDateTimeException() {
        // This test tries to trigger DateTimeException by adding millis that cause Instant out of range
        // The max Instant is 31556889864403199 seconds + nanos
        Instant maxInstant = Instant.ofEpochSecond(31556889864403199L, 999_999_999);
        try {
            maxInstant.plusMillis(1L);
            throw new AssertionError("Expected DateTimeException not thrown for plusMillis overflow beyond max Instant");
        } catch (DateTimeException expected) {
            // expected
        }

        Instant minInstant = Instant.ofEpochSecond(-31557014167219200L, 0);
        try {
            minInstant.plusMillis(-1L);
            throw new AssertionError("Expected DateTimeException not thrown for plusMillis overflow beyond min Instant");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
