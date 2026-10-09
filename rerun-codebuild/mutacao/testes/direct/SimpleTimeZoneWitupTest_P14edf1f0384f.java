/* @test    @summary Test setDSTSavings method of SimpleTimeZone for valid and invalid inputs    @run main SimpleTimeZoneWitupTest_P14edf1f0384f */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P14edf1f0384f {

    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }

    static void testSetDSTSavingsValid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        int validDSTSavings = 3600000; // 1 hour in milliseconds
        tz.setDSTSavings(validDSTSavings);
        if (tz.getDSTSavings() != validDSTSavings) {
            throw new AssertionError("Expected DSTSavings to be " + validDSTSavings + " but was " + tz.getDSTSavings());
        }
    }

    static void testSetDSTSavingsInvalid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        int invalidDSTSavings = 0;
        try {
            tz.setDSTSavings(invalidDSTSavings);
            throw new AssertionError("Expected IllegalArgumentException for zero DSTSavings");
        } catch (IllegalArgumentException e) {
            // expected
        }
        int negativeDSTSavings = -1000;
        try {
            tz.setDSTSavings(negativeDSTSavings);
            throw new AssertionError("Expected IllegalArgumentException for negative DSTSavings");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
