/*
 * @test
 * @summary Test NumberFormat.getCurrency() throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.getCurrency();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
