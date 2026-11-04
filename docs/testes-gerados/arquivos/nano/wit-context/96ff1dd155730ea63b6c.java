/* @test    @summary Test for getRoundingMode method in NumberFormat class    @run main NumberFormatWitupTest */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testGetRoundingModeThrows();
    }

    public static void testGetRoundingModeThrows() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            RoundingMode rm = nf.getRoundingMode();
            System.out.println("Unexpected success: getRoundingMode() returned: " + rm);
        } catch (UnsupportedOperationException e) {
            System.out.println("Expected exception caught: " + e.getClass().getName());
        } catch (Throwable t) {
            System.out.println("Unexpected exception type: " + t.getClass().getName());
        }
    }
}
