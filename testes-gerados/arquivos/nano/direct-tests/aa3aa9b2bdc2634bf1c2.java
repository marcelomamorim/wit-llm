/* @test    @summary Test setting currency in DecimalFormatSymbols    @run main DecimalFormatSymbolsWitupTest */

import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest {

    public static void main(String[] args) {
        testSetCurrencyWithValidCurrency();
        testSetCurrencyWithNull();
    }

    static void testSetCurrencyWithValidCurrency() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency usd = Currency.getInstance("USD");
        dfs.setCurrency(usd);
        assert dfs.getCurrency().equals(usd) : "Currency should be USD";
        assert dfs.getCurrencySymbol().equals("$") : "Currency symbol should be $";
        assert dfs.getInternationalCurrencySymbol().equals("USD") : "International currency symbol should be USD";
    }

    static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        try {
            dfs.setCurrency(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
