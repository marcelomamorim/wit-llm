/* @test    @summary Test for DecimalFormat.setRoundingMode with null input to verify NullPointerException is thrown    @run main DecimalFormatWitupTest_Pda2732df3091 */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest_Pda2732df3091 {

    public static void main(String[] args) {
        testSetRoundingModeNull();
    }

    public static void testSetRoundingModeNull() {
        DecimalFormat df = new DecimalFormat();
        try {
            df.setRoundingMode(null);
            System.err.println("Expected NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
