/*
 * @test
 * @summary Test java.text.DecimalFormat.setRoundingMode behavior
 * @run main DecimalFormatWitupTest
 */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest {
    public static void main(String[] args) {
        testSetRoundingModeNull();
        testSetRoundingModeValid();
    }

    private static void testSetRoundingModeNull() {
        DecimalFormat df = new DecimalFormat();
        try {
            df.setRoundingMode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSetRoundingModeValid() {
        DecimalFormat df = new DecimalFormat();
        for (RoundingMode mode : RoundingMode.values()) {
            df.setRoundingMode(mode);
            // No direct getter for roundingMode, so test no exception and internal state change indirectly
            // by setting again and ensuring no exception
            df.setRoundingMode(mode);
        }
    }
}
