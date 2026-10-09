/* @test    @summary Test for setCurrency method in DecimalFormatSymbols class    @run main DecimalFormatSymbolsWitupTest_Pfb1b76764fa5 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest_Pfb1b76764fa5 {
    public static void main(String[] args) {
        testSetCurrencyWithValidCurrency();
        testSetCurrencyWithNull();
    }

    private static void testSetCurrencyWithValidCurrency() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency usd = Currency.getInstance("USD");
        dfs.setCurrency(usd);
        assert dfs.getCurrency().equals(usd) : "Currency should be USD";
        assert dfs.getCurrencySymbol().equals("$") : "Currency symbol should be $";
        assert dfs.getInternationalCurrencySymbol().equals("USD") : "International currency symbol should be USD";
    }

    private static void testSetCurrencyWithNull() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        boolean exceptionThrown = false;
        try {
            dfs.setCurrency(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException should be thrown when setting null currency";
    }
}
