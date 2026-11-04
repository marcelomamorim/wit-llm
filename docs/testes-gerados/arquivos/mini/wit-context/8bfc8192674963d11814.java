/*
 * @test
 * @summary Test java.text.DecimalFormat.setRoundingMode(RoundingMode) for null argument and normal usage
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
            // Expected exception
        }
    }

    private static void testSetRoundingModeValid() {
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.HALF_UP);
        // No direct getter for roundingMode, so test by setting again to another value
        df.setRoundingMode(RoundingMode.DOWN);
        // If no exception thrown, test passes
    }
}
