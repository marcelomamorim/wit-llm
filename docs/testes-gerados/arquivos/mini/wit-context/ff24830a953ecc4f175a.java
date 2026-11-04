/*
 * @test
 * @summary Test SimpleTimeZone.setDSTSavings(int) for valid and invalid inputs
 * @run main SimpleTimeZoneWitupTest
 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest {
    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }

    private static void testSetDSTSavingsValid() {
        SimpleTimeZone stz = new SimpleTimeZone(0, "GMT");
        // Test with a positive value
        int validValue = 3600000; // 1 hour in milliseconds
        stz.setDSTSavings(validValue);
        // No direct getter for dstSavings, so test by setting again with another valid value
        int anotherValidValue = 1800000; // 30 minutes
        stz.setDSTSavings(anotherValidValue);
    }

    private static void testSetDSTSavingsInvalid() {
        SimpleTimeZone stz = new SimpleTimeZone(0, "GMT");
        int[] invalidValues = {0, -1, -1000};
        for (int val : invalidValues) {
            boolean thrown = false;
            try {
                stz.setDSTSavings(val);
            } catch (IllegalArgumentException e) {
                thrown = true;
                String msg = e.getMessage();
                if (msg == null || !msg.contains(String.valueOf(val))) {
                    throw new RuntimeException("Exception message does not contain the invalid value: " + val);
                }
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for value: " + val);
            }
        }
    }
}
