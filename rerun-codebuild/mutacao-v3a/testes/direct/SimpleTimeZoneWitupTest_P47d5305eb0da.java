/* @test    @summary Test setDSTSavings with valid and invalid values    @run main SimpleTimeZoneWitupTest_P47d5305eb0da */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P47d5305eb0da {

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
        int[] invalidValues = {0, -100, -1};
        for (int invalidValue : invalidValues) {
            try {
                tz.setDSTSavings(invalidValue);
                throw new AssertionError("Expected IllegalArgumentException for value: " + invalidValue);
            } catch (IllegalArgumentException e) {
                // expected
            }
        }
    }
}
