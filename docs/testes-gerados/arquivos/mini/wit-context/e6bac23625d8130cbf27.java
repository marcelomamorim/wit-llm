/*
 * @test
 * @summary Test java.time.ZoneOffset.ofHours(int) for valid and invalid inputs
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        testValidOffsets();
        testInvalidOffsets();
        System.out.println("All tests passed.");
    }

    private static void testValidOffsets() {
        // Test valid offsets within range [-18, 18]
        for (int h = -18; h <= 18; h++) {
            ZoneOffset offset = ZoneOffset.ofHours(h);
            // The total seconds should be hours * 3600
            int expectedSeconds = h * 3600;
            if (offset.getTotalSeconds() != expectedSeconds) {
                throw new AssertionError("Expected total seconds " + expectedSeconds + " but got " + offset.getTotalSeconds() + " for hours=" + h);
            }
        }
    }

    private static void testInvalidOffsets() {
        // Test values outside the valid range [-18, 18] should throw DateTimeException
        int[] invalidHours = {Integer.MIN_VALUE, -19, 19, Integer.MAX_VALUE};
        for (int h : invalidHours) {
            boolean thrown = false;
            try {
                ZoneOffset.ofHours(h);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for hours=" + h);
            }
        }
    }
}
