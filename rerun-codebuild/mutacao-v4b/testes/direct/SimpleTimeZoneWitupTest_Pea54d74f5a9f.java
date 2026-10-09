/* @test
   @summary Verify setDSTSavings method with valid and invalid inputs.
   @run main SimpleTimeZoneWitupTest_Pea54d74f5a9f
 */
import java.util.SimpleTimeZone;
public class SimpleTimeZoneWitupTest_Pea54d74f5a9f {
    public static void main(String[] args) {
        testSetDSTSavingsValid();
        testSetDSTSavingsInvalid();
    }
    private static void testSetDSTSavingsValid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        tz.setDSTSavings(3600000); // 1 hour in milliseconds
        if (tz.getDSTSavings() != 3600000) {
            throw new AssertionError("Expected DSTSavings to be 3600000, but was " + tz.getDSTSavings());
        }
    }
    private static void testSetDSTSavingsInvalid() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        try {
            tz.setDSTSavings(0);
            throw new AssertionError("Expected IllegalArgumentException for zero value");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            tz.setDSTSavings(-1000);
            throw new AssertionError("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
