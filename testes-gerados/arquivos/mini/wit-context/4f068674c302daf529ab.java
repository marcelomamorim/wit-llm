/*
 * @test
 * @summary Test java.time.ZoneOffset.ofTotalSeconds(int) for valid and invalid inputs
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        testValidOffsets();
        testInvalidOffsets();
    }

    private static void testValidOffsets() {
        // Test zero offset
        ZoneOffset zero = ZoneOffset.ofTotalSeconds(0);
        if (!"Z".equals(zero.getId())) {
            throw new AssertionError("Expected ID 'Z' for zero offset, got: " + zero.getId());
        }

        // Test minimum valid offset (-18 hours)
        int minSeconds = -18 * 3600;
        ZoneOffset minOffset = ZoneOffset.ofTotalSeconds(minSeconds);
        if (minOffset.getTotalSeconds() != minSeconds) {
            throw new AssertionError("Expected totalSeconds " + minSeconds + ", got: " + minOffset.getTotalSeconds());
        }

        // Test maximum valid offset (+18 hours)
        int maxSeconds = 18 * 3600;
        ZoneOffset maxOffset = ZoneOffset.ofTotalSeconds(maxSeconds);
        if (maxOffset.getTotalSeconds() != maxSeconds) {
            throw new AssertionError("Expected totalSeconds " + maxSeconds + ", got: " + maxOffset.getTotalSeconds());
        }

        // Test a valid offset that is multiple of 15 minutes (e.g. +2:30)
        int offsetSeconds = 2 * 3600 + 30 * 60;
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(offsetSeconds);
        if (offset.getTotalSeconds() != offsetSeconds) {
            throw new AssertionError("Expected totalSeconds " + offsetSeconds + ", got: " + offset.getTotalSeconds());
        }

        // Test a valid offset that is NOT multiple of 15 minutes (e.g. +2:37)
        int offsetNonMultiple = 2 * 3600 + 37 * 60;
        ZoneOffset offsetNon = ZoneOffset.ofTotalSeconds(offsetNonMultiple);
        if (offsetNon.getTotalSeconds() != offsetNonMultiple) {
            throw new AssertionError("Expected totalSeconds " + offsetNonMultiple + ", got: " + offsetNon.getTotalSeconds());
        }
    }

    private static void testInvalidOffsets() {
        // Test just below minimum valid offset (-18 hours - 1 second)
        int belowMin = -18 * 3600 - 1;
        try {
            ZoneOffset.ofTotalSeconds(belowMin);
            throw new AssertionError("Expected DateTimeException for totalSeconds " + belowMin);
        } catch (DateTimeException expected) {
            // expected
        }

        // Test just above maximum valid offset (+18 hours + 1 second)
        int aboveMax = 18 * 3600 + 1;
        try {
            ZoneOffset.ofTotalSeconds(aboveMax);
            throw new AssertionError("Expected DateTimeException for totalSeconds " + aboveMax);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
