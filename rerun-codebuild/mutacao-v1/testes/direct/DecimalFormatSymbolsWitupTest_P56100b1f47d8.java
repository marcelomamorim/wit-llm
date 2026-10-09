/* @test    @summary Test setting currency in DecimalFormatSymbols    @run main DecimalFormatSymbolsWitupTest_P56100b1f47d8 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;

public class DecimalFormatSymbolsWitupTest_P56100b1f47d8 {
    public static void main(String[] args) {
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            Currency usd = Currency.getInstance("USD");
            dfs.setCurrency(usd);
            if (!dfs.getCurrency().equals(usd)) {
                throw new AssertionError("Currency not set correctly");
            }
            // Test setting null currency throws NullPointerException
            try {
                dfs.setCurrency(null);
                throw new AssertionError("Expected NullPointerException not thrown");
            } catch (NullPointerException e) {
                // Expected exception
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
