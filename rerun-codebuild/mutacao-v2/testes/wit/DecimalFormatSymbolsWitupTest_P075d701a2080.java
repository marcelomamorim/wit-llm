/*
 * @test
 * @summary Test DecimalFormatSymbols.setCurrency behavior including null argument check
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
        String intlCode = usd.getCurrencyCode();
        String symbol = usd.getSymbol(Locale.US);
        if (!intlCode.equals(dfs.getInternationalCurrencySymbol())) {
            throw new RuntimeException("intlCurrencySymbol not updated correctly");
        }
        if (!symbol.equals(dfs.getCurrencySymbol())) {
            throw new RuntimeException("currencySymbol not updated correctly");
        }
    }

    private static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        try {
            dfs.setCurrency(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
