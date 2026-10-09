/* @test    @summary Test for SimpleTimeZone.setDSTSavings(int) with valid and invalid inputs    @run main SimpleTimeZoneWitupTest_P47d5305eb0da */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P47d5305eb0da {

    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }

    static void testSetDSTSavingsValid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        tz.setDSTSavings(3600000); // 1 hour
        if (tz.getDSTSavings() != 3600000) {
            throw new AssertionError("Expected DST savings to be 3600000, but was " + tz.getDSTSavings());
        }
    }

    static void testSetDSTSavingsInvalid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT");
        try {
            tz.setDSTSavings(0);
            throw new AssertionError("Expected IllegalArgumentException for input 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        try {
            tz.setDSTSavings(-1000); // negative value
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
