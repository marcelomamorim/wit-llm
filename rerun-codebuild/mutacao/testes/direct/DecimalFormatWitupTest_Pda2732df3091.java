/* @test    @summary Test setRoundingMode method of DecimalFormat for JDK 11+28    @run main DecimalFormatWitupTest_Pda2732df3091 */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest_Pda2732df3091 {

    public static void main(String[] args) {
        testSetRoundingModeWithValidValue();
        testSetRoundingModeWithNull();
    }

    static void testSetRoundingModeWithValidValue() {
        DecimalFormat df = new DecimalFormat();
        try {
            df.setRoundingMode(RoundingMode.HALF_UP);
            assert df != null : "DecimalFormat instance should not be null";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid RoundingMode", e);
        }
    }

    static void testSetRoundingModeWithNull() {
        DecimalFormat df = new DecimalFormat();
        boolean exceptionThrown = false;
        try {
            df.setRoundingMode(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown when setting null RoundingMode";
    }
}
