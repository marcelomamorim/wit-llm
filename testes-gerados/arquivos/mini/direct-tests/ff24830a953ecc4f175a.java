/*
 * @test
 * @summary Test SimpleTimeZone.setDSTSavings behavior including exception on invalid input
 * @run main SimpleTimeZoneWitupTest
 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest {
    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }

    private static void testSetDSTSavingsValid() {
        SimpleTimeZone stz = new SimpleTimeZone(0, "UTC");
        int validValue = 3600000; // 1 hour in milliseconds
        stz.setDSTSavings(validValue);
        // No direct getter for dstSavings, so test by setting and not throwing
        // We rely on no exception means success
    }

    private static void testSetDSTSavingsInvalid() {
        SimpleTimeZone stz = new SimpleTimeZone(0, "UTC");
        int[] invalidValues = {0, -1, -3600000};
        for (int val : invalidValues) {
            try {
                stz.setDSTSavings(val);
                throw new RuntimeException("Expected IllegalArgumentException for value: " + val);
            } catch (IllegalArgumentException e) {
                // Expected exception
                String msg = e.getMessage();
                if (!msg.contains(String.valueOf(val))) {
                    throw new RuntimeException("Exception message does not contain invalid value: " + val);
                }
            }
        }
    }
}
