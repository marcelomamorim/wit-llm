/* @test    @summary Test for NumberFormat.setCurrency method to verify exception throwing behavior    @run main NumberFormatWitupTest */

import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatWitupTest {

    public static void main(String[] args) {
        testSetCurrencyThrowsException();
    }

    public static void testSetCurrencyThrowsException() {
        NumberFormat nf = NumberFormat.getInstance();
        try {
            nf.setCurrency(Currency.getInstance("USD"));
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
