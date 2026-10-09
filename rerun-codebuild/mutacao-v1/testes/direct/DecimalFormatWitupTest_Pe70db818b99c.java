/* @test    @summary Test setRoundingMode method of DecimalFormat for JDK 11+28    @run main DecimalFormatWitupTest_Pe70db818b99c */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest_Pe70db818b99c {
    public static void main(String[] args) {
        try {
            DecimalFormat df = new DecimalFormat();
            // Test setting a valid RoundingMode
            df.setRoundingMode(RoundingMode.HALF_UP);
            if (df.getRoundingMode() != RoundingMode.HALF_UP) {
                throw new AssertionError("RoundingMode not set correctly to HALF_UP");
            }
            // Test setting another valid RoundingMode
            df.setRoundingMode(RoundingMode.DOWN);
            if (df.getRoundingMode() != RoundingMode.DOWN) {
                throw new AssertionError("RoundingMode not set correctly to DOWN");
            }
            // Test setting null RoundingMode should throw NullPointerException
            boolean exceptionThrown = false;
            try {
                df.setRoundingMode(null);
            } catch (NullPointerException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected NullPointerException when setting null RoundingMode");
            }
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage(), e);
        }
    }
}
