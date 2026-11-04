/*
 * @test
 * @summary Test getCurrency() method of NumberFormat class.
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testGetCurrencyThrowsException();
    }

    public static void testGetCurrencyThrowsException() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.getCurrency();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
