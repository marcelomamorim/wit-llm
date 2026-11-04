/* @test    @summary Test for NumberFormat.setRoundingMode method to verify exception throwing behavior    @run main NumberFormatWitupTest */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testSetRoundingModeThrowsException();
    }

    private static void testSetRoundingModeThrowsException() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.setRoundingMode(RoundingMode.HALF_UP);
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.err.println("FAIL: Caught unexpected exception: " + e);
            System.exit(1);
        }
    }
}
