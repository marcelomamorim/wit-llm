/*
 * @test
 * @summary Test java.text.DecimalFormatSymbols.setCurrency(Currency) method
 * @run main DecimalFormatSymbolsWitupTest_P075d701a2080
 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest_P075d701a2080 {
    public static void main(String[] args) {
        testSetCurrencyWithValidCurrency();
        testSetCurrencyWithNull();
    }

    private static void testSetCurrencyWithValidCurrency() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency usd = Currency.getInstance("USD");
        dfs.setCurrency(usd);
        // Check that currencySymbol and intlCurrencySymbol are updated accordingly
        String intlCode = dfs.getInternationalCurrencySymbol();
        String symbol = dfs.getCurrencySymbol();
        if (!"USD".equals(intlCode)) {
            throw new RuntimeException("Expected international currency code USD but got " + intlCode);
        }
        if (!"$".equals(symbol)) {
            throw new RuntimeException("Expected currency symbol $ but got " + symbol);
        }
    }

    private static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        try {
            dfs.setCurrency(null);
            throw new RuntimeException("Expected NullPointerException when setting null currency");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
