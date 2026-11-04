/*
 * @test
 * @summary Test java.time.ZoneOffset.ofTotalSeconds(int) method
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        testValidCacheOffsets();
        testValidNonCacheOffsets();
        testInvalidOffsets();
    }

    private static void testValidCacheOffsets() {
        // multiples of 15 minutes in range should return cached instances
        int[] validSeconds = {0, 15 * 60, -15 * 60, 18 * 3600, -18 * 3600};
        for (int totalSeconds : validSeconds) {
            ZoneOffset z1 = ZoneOffset.ofTotalSeconds(totalSeconds);
            ZoneOffset z2 = ZoneOffset.ofTotalSeconds(totalSeconds);
            if (z1 == null) {
                throw new AssertionError("Returned null for totalSeconds: " + totalSeconds);
            }
            if (z1 != z2) {
                throw new AssertionError("Expected cached instance for totalSeconds: " + totalSeconds);
            }
            if (z1.getTotalSeconds() != totalSeconds) {
                throw new AssertionError("Offset seconds mismatch for totalSeconds: " + totalSeconds);
            }
        }
    }

    private static void testValidNonCacheOffsets() {
        // offsets not multiple of 15 minutes should return new instances
        int[] nonCacheSeconds = {1, -1, 14 * 60 + 1, -14 * 60 - 1, 17 * 3600 + 1, -17 * 3600 - 1};
        for (int totalSeconds : nonCacheSeconds) {
            ZoneOffset z1 = ZoneOffset.ofTotalSeconds(totalSeconds);
            ZoneOffset z2 = ZoneOffset.ofTotalSeconds(totalSeconds);
            if (z1 == null) {
                throw new AssertionError("Returned null for totalSeconds: " + totalSeconds);
            }
            if (z1 == z2) {
                throw new AssertionError("Expected distinct instances for non-cache totalSeconds: " + totalSeconds);
            }
            if (z1.getTotalSeconds() != totalSeconds) {
                throw new AssertionError("Offset seconds mismatch for totalSeconds: " + totalSeconds);
            }
            if (z2.getTotalSeconds() != totalSeconds) {
                throw new AssertionError("Offset seconds mismatch for totalSeconds: " + totalSeconds);
            }
        }
    }

    private static void testInvalidOffsets() {
        int[] invalidSeconds = {-(18 * 3600) - 1, 18 * 3600 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int totalSeconds : invalidSeconds) {
            boolean thrown = false;
            try {
                ZoneOffset.ofTotalSeconds(totalSeconds);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for totalSeconds: " + totalSeconds);
            }
        }
    }
}
