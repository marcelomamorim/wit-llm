/* @test
   @summary Tests the setCurrency method of DecimalFormatSymbols.
   @run main DecimalFormatSymbolsWitupTest_P1bfeeb788039
 */
import java.util.Currency;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest_P1bfeeb788039 {
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
        try {
            dfs.setCurrency(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
