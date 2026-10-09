/* @test
   @summary Verifies behavior of ZoneOffset.ofTotalSeconds.
   @run main ZoneOffsetWitupTest_P37ec03090b57
 */
import java.time.ZoneOffset;
import java.time.DateTimeException;
public class ZoneOffsetWitupTest_P37ec03090b57 {
    public static void main(String[] args) {
        testValidRange();
        testBelowMinimum();
        testAboveMaximum();
        testValidMultiple();
        testInvalidMultiple();
    }
    static void testValidRange() {
        try {
            ZoneOffset.ofTotalSeconds(0);
            ZoneOffset.ofTotalSeconds(SECONDS_PER_HOUR * 18);
            ZoneOffset.ofTotalSeconds(-SECONDS_PER_HOUR * 18);
        } catch (Exception e) {
            throw new AssertionError("Valid range threw exception", e);
        }
    }
    static void testBelowMinimum() {
        try {
            ZoneOffset.ofTotalSeconds(-SECONDS_PER_HOUR * 18 - 1);
            throw new AssertionError("Expected DateTimeException for below minimum");
        } catch (DateTimeException e) {
            // expected
        }
    }
    static void testAboveMaximum() {
        try {
            ZoneOffset.ofTotalSeconds(SECONDS_PER_HOUR * 18 + 1);
            throw new AssertionError("Expected DateTimeException for above maximum");
        } catch (DateTimeException e) {
            // expected
        }
    }
    static void testValidMultiple() {
        try {
            ZoneOffset.ofTotalSeconds(SECONDS_PER_MINUTE * 15 * 2);
            ZoneOffset.ofTotalSeconds(-SECONDS_PER_MINUTE * 15 * 2);
        } catch (Exception e) {
            throw new AssertionError("Valid multiple of 15 minutes threw exception", e);
        }
    }
    static void testInvalidMultiple() {
        try {
            ZoneOffset.ofTotalSeconds(SECONDS_PER_MINUTE * 15 * 2 + 1);
            throw new AssertionError("Expected DateTimeException for invalid multiple");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
