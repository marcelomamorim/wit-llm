/*
 * @test
 * @summary Test NumberFormat.setRoundingMode throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.math.RoundingMode;

public class NumberFormatWitupTest {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.setRoundingMode(RoundingMode.HALF_UP);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
