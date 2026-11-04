/*
 * @test
 * @summary Test NumberFormat.setCurrency throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {
    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        Currency currency = Currency.getInstance("USD");
        try {
            nf.setCurrency(currency);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
