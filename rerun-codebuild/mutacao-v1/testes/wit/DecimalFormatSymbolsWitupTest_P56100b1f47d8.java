/* @test    @summary Test for DecimalFormatSymbols methods and exception paths    @run main DecimalFormatSymbolsWitupTest_P56100b1f47d8 */

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Currency;

public class DecimalFormatSymbolsWitupTest_P56100b1f47d8 {

    public static void main(String[] args) {
        testSetCurrencyWithNull();
        testSetCurrencyWithValidCurrency();
    }

    private static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        boolean exceptionThrown = false;
        try {
            dfs.setCurrency(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown as expected");
        }
    }

    private static void testSetCurrencyWithValidCurrency() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency usd = Currency.getInstance("USD");
        dfs.setCurrency(usd);
        if (!usd.equals(dfs.getCurrency())) {
            throw new AssertionError("Currency not set correctly");
        }
        if (!usd.getCurrencyCode().equals(dfs.getInternationalCurrencySymbol())) {
            throw new AssertionError("International currency symbol mismatch");
        }
        if (!usd.getSymbol(Locale.US).equals(dfs.getCurrencySymbol())) {
            throw new AssertionError("Currency symbol mismatch");
        }
    }
}
