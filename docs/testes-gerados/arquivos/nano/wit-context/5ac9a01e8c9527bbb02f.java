/*
 * @test
 * @summary Test for Instant.plusMillis(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testPlusMillisNormal();
        testPlusMillisZero();
        testPlusMillisNegative();
        testPlusMillisOverflow();
    }

    static void testPlusMillisNormal() {
        Instant base = Instant.ofEpochSecond(1000L, 500_000_000L);
        long millisToAdd = 1500L;
        Instant result = base.plusMillis(millisToAdd);
        long expectedSeconds = 1000 + (millisToAdd / 1000);
        long expectedNanos = 500_000_000L + (millisToAdd % 1000) * 1_000_000;
        long expectedNanosAdjusted = expectedNanos >= 1_000_000_000L ? expectedNanos - 1_000_000_000L : expectedNanos;
        long expectedSecondsAdjusted = expectedNanos >= 1_000_000_000L ? expectedSeconds + 1 : expectedSeconds;
        assert result.getEpochSecond() == expectedSecondsAdjusted : "Failed: normal addition";
        assert result.getNano() == (int) expectedNanosAdjusted : "Failed: normal addition";
    }

    static void testPlusMillisZero() {
        Instant base = Instant.ofEpochSecond(1234L, 567_890_000L);
        Instant result = base.plusMillis(0L);
        assert result.equals(base) : "Failed: zero millis";
    }

    static void testPlusMillisNegative() {
        Instant base = Instant.ofEpochSecond(2000L, 0L);
        long millisToAdd = -1500L;
        Instant result = base.plusMillis(millisToAdd);
        long expectedSeconds = 2000 + (millisToAdd / 1000);
        long expectedNanos = 0 + (millisToAdd % 1000) * 1_000_000;
        long expectedSecondsAdjusted = expectedNanos < 0 ? expectedSeconds - 1 : expectedSeconds;
        long expectedNanosAdjusted = expectedNanos < 0 ? expectedNanos + 1_000_000_000L : expectedNanos;
        assert result.getEpochSecond() == expectedSecondsAdjusted : "Failed: negative addition";
        assert result.getNano() == (int) expectedNanosAdjusted : "Failed: negative addition";
    }

    static void testPlusMillisOverflow() {
        Instant base = Instant.ofEpochSecond(Long.MAX_VALUE - 1, 999_999_999);
        long millisToAdd = 2000L; // should cause overflow to Long.MAX_VALUE + 1, but capped at Long.MAX_VALUE
        try {
            Instant result = base.plusMillis(millisToAdd);
            long expectedSeconds = Long.MAX_VALUE;
            long expectedNanos = 999_999_999;
            assert result.getEpochSecond() == expectedSeconds : "Failed: overflow seconds";
            assert result.getNano() == (int) expectedNanos : "Failed: overflow nanos";
        } catch (ArithmeticException e) {
            // Acceptable if overflow causes exception
        }
    }
}
