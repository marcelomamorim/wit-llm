/*
 * @test
 * @summary Tests for java.time.Instant.plusSeconds(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testPlusSecondsNormal();
        testPlusSecondsZero();
        testPlusSecondsLarge();
        testPlusSecondsNegative();
        testPlusSecondsOverflow();
    }

    static void testPlusSecondsNormal() {
        Instant base = Instant.ofEpochSecond(1000L);
        Instant result = base.plusSeconds(10L);
        if (!result.equals(Instant.ofEpochSecond(1010L))) {
            throw new AssertionError("Expected 1010, got " + result.getEpochSecond());
        }
    }

    static void testPlusSecondsZero() {
        Instant base = Instant.ofEpochSecond(2000L);
        Instant result = base.plusSeconds(0L);
        if (!result.equals(base)) {
            throw new AssertionError("Expected same instant, got " + result.getEpochSecond());
        }
    }

    static void testPlusSecondsLarge() {
        Instant base = Instant.ofEpochSecond(0L);
        Instant result = base.plusSeconds(31556926L); // one year in seconds
        if (!result.equals(Instant.ofEpochSecond(31556926L))) {
            throw new AssertionError("Expected 31556926, got " + result.getEpochSecond());
        }
    }

    static void testPlusSecondsNegative() {
        Instant base = Instant.ofEpochSecond(1000L);
        Instant result = base.plusSeconds(-500L);
        if (!result.equals(Instant.ofEpochSecond(500L))) {
            throw new AssertionError("Expected 500, got " + result.getEpochSecond());
        }
    }

    static void testPlusSecondsOverflow() {
        try {
            Instant base = Instant.ofEpochSecond(Long.MAX_VALUE - 1);
            base.plusSeconds(10L);
            throw new AssertionError("Expected ArithmeticException for overflow");
        } catch (ArithmeticException e) {
            // expected
        }
        try {
            Instant base = Instant.ofEpochSecond(Long.MIN_VALUE + 1);
            base.plusSeconds(-10L);
            throw new AssertionError("Expected ArithmeticException for underflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
