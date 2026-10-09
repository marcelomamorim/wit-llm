/* @test    @summary Test for DecimalFormatSymbols methods and exception paths    @run main DecimalFormatSymbolsWitupTest_P6d286a0dbf39 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;

public class DecimalFormatSymbolsWitupTest_P6d286a0dbf39 {

    public static void main(String[] args) {
        testSetCurrencyWithNull();
        testSetCurrencyWithValidCurrency();
    }

    private static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        boolean exceptionThrown = false;
        try {
            dfs.setCurrency(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when setting null currency");
        }
    }

    private static void testSetCurrencyWithValidCurrency() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        Currency currency = Currency.getInstance("USD");
        dfs.setCurrency(currency);
        if (!currency.equals(dfs.getCurrency())) {
            throw new AssertionError("Currency not set correctly");
        }
    }
}
