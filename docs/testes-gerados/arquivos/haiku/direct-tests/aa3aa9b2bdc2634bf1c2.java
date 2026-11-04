/*
 * @test
 * @summary Verifies DecimalFormatSymbols.setCurrency(Currency) null handling and observable currency updates
 * @run main DecimalFormatSymbolsWitupTest
 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCurrencyThrowsNPE();
        testSetCurrencyUpdatesCurrencyCode();
        testSetCurrencyUpdatesCurrencySymbol();
    }

    static void testNullCurrencyThrowsNPE() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        boolean thrown = false;
        try {
            dfs.setCurrency(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null currency");
        }
    }

    static void testSetCurrencyUpdatesCurrencyCode() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency eur = Currency.getInstance("EUR");
        dfs.setCurrency(eur);
        if (!"EUR".equals(dfs.getCurrency().getCurrencyCode())) {
            throw new RuntimeException("getCurrency() code mismatch: " + dfs.getCurrency().getCurrencyCode());
        }
        if (!"EUR".equals(dfs.getInternationalCurrencySymbol())) {
            throw new RuntimeException("International currency symbol mismatch: "
                    + dfs.getInternationalCurrencySymbol());
        }
    }

    static void testSetCurrencyUpdatesCurrencySymbol() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        Currency eur = Currency.getInstance("EUR");
        dfs.setCurrency(eur);
        String expected = eur.getSymbol(Locale.US);
        if (!expected.equals(dfs.getCurrencySymbol())) {
            throw new RuntimeException("Currency symbol mismatch: expected " + expected
                    + " but was " + dfs.getCurrencySymbol());
        }
    }
}
