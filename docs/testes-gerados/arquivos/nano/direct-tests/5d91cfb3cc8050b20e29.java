/*
 * @test
 * @summary Test setRoundingMode method of NumberFormat class.
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testSetRoundingModeThrowsUnsupported();
    }

    public static void testSetRoundingModeThrowsUnsupported() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.setRoundingMode(RoundingMode.HALF_UP);
            // Expecting UnsupportedOperationException
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
