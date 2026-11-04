/* @test    @summary Test for NumberFormat.getCurrency() method to observe behavior and exception handling    @run main NumberFormatWitupTest */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testGetCurrencyThrowsException();
    }

    private static void testGetCurrencyThrowsException() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            Currency c = nf.getCurrency();
            System.out.println("Unexpected success: getCurrency() returned " + c);
        } catch (UnsupportedOperationException e) {
            System.out.println("Expected exception caught: " + e);
        } catch (Exception e) {
            System.out.println("Unexpected exception type: " + e);
        }
    }
}
