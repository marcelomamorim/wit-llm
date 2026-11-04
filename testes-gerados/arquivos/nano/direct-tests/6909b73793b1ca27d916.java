/*
 * @test
 * @summary Test setCurrency method of NumberFormat throws UnsupportedOperationException
 * @run main NumberFormatWitupTest
 */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testSetCurrencyThrows();
    }

    public static void testSetCurrencyThrows() {
        NumberFormat nf = NumberFormat.getInstance();
        Currency currency = Currency.getInstance("USD");
        try {
            nf.setCurrency(currency);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
