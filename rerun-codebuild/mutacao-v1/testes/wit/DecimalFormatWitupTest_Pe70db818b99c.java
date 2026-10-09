/* @test    @summary Test for DecimalFormat.setRoundingMode with null input to verify NullPointerException is thrown    @run main DecimalFormatWitupTest_Pe70db818b99c */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest_Pe70db818b99c {

    public static void main(String[] args) {
        testSetRoundingModeNull();
    }

    private static void testSetRoundingModeNull() {
        DecimalFormat df = new DecimalFormat();
        boolean threwException = false;
        try {
            df.setRoundingMode(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("NullPointerException was expected but not thrown");
        }
    }
}
