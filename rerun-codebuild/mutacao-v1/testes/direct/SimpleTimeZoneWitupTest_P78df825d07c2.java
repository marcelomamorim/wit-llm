/* @test    @summary Test setDSTSavings with valid and invalid inputs    @run main SimpleTimeZoneWitupTest_P78df825d07c2 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P78df825d07c2 {

    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }

    static void testSetDSTSavingsValid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        try {
            tz.setDSTSavings(3600000); // 1 hour in milliseconds
            if (tz.getDSTSavings() != 3600000) {
                throw new AssertionError("Expected DSTSavings to be 3600000, but was " + tz.getDSTSavings());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    static void testSetDSTSavingsInvalid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        try {
            tz.setDSTSavings(0); // invalid, should throw
            throw new AssertionError("Expected IllegalArgumentException for zero input");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            tz.setDSTSavings(-1000); // invalid, should throw
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
