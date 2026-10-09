/* @test    @summary Test for SimpleTimeZone.setDSTSavings to verify exception on non-positive input    @run main SimpleTimeZoneWitupTest_P78df825d07c2 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P78df825d07c2 {

    public static void main(String[] args) {
        testSetDSTSavingsThrowsOnZero();
        testSetDSTSavingsThrowsOnNegative();
        testSetDSTSavingsValidPositive();
    }

    private static void testSetDSTSavingsThrowsOnZero() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestZone");
        try {
            tz.setDSTSavings(0);
            throw new AssertionError("Expected IllegalArgumentException for input 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testSetDSTSavingsThrowsOnNegative() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestZone");
        try {
            tz.setDSTSavings(-1000);
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testSetDSTSavingsValidPositive() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestZone");
        int validValue = 3600000; // 1 hour in milliseconds
        tz.setDSTSavings(validValue);
        if (tz.getDSTSavings() != validValue) {
            throw new AssertionError("DSTSavings not set correctly for valid input");
        }
    }
}
