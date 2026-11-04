/* @test    @summary Test getRoundingMode method of NumberFormat class    @run main NumberFormatWitupTest */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testGetRoundingMode();
    }

    public static void testGetRoundingMode() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            RoundingMode mode = nf.getRoundingMode();
            // Since the method throws UnsupportedOperationException, reaching here indicates failure
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("getRoundingMode() correctly throws UnsupportedOperationException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
