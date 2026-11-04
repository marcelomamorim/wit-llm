/*
 * @test
 * @summary Test NumberFormat.getRoundingMode throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            RoundingMode rm = nf.getRoundingMode();
            throw new RuntimeException("Expected UnsupportedOperationException but got: " + rm);
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
