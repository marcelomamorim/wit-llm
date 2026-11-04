/*
 * @test
 * @summary Tests for java.time.Instant.ofEpochSecond(long)
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testValidEpochSeconds();
        testEpochSecondsBelowMinimum();
        testEpochSecondsAboveMaximum();
    }

    private static void testValidEpochSeconds() {
        long[] validValues = {0L, -31557014167219200L, 31556889864403199L, 1609459200L}; // 1970-01-01T00:00:00Z, min, max, arbitrary
        for (long epochSecond : validValues) {
            Instant instant = Instant.ofEpochSecond(epochSecond);
            if (instant == null) {
                throw new AssertionError("Expected non-null Instant for epochSecond=" + epochSecond);
            }
        }
    }

    private static void testEpochSecondsBelowMinimum() {
        long belowMin = -31557014167219201L; // just below min
        try {
            Instant.ofEpochSecond(belowMin);
            throw new AssertionError("Expected DateTimeException for epochSecond=" + belowMin);
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    private static void testEpochSecondsAboveMaximum() {
        long aboveMax = 31556889864403200L; // just above max
        try {
            Instant.ofEpochSecond(aboveMax);
            throw new AssertionError("Expected DateTimeException for epochSecond=" + aboveMax);
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
