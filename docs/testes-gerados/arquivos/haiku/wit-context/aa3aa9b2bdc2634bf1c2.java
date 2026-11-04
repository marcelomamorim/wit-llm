/*
 * @test
 * @summary Verifies DecimalFormatSymbols.setCurrency rejects null with NullPointerException and keeps prior state unchanged on failure
 * @run main DecimalFormatSymbolsWitupTest
 */

import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;

public class DecimalFormatSymbolsWitupTest {

    public static void main(String[] args) {
        testNullCurrencyThrowsNPE();
        testSetCurrencyUpdatesCurrencyCodes();
        testNullCurrencyDoesNotChangePriorState();
    }

    static void testNullCurrencyThrowsNPE() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        try {
            dfs.setCurrency(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null currency");
    }

    static void testSetCurrencyUpdatesCurrencyCodes() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        Currency eur = Currency.getInstance("EUR");
        dfs.setCurrency(eur);
        if (!"EUR".equals(dfs.getInternationalCurrencySymbol())) {
            throw new RuntimeException("Unexpected international currency symbol: "
                    + dfs.getInternationalCurrencySymbol());
        }
        if (!"EUR".equals(dfs.getCurrency().getCurrencyCode())) {
            throw new RuntimeException("Unexpected currency code: "
                    + dfs.getCurrency().getCurrencyCode());
        }
    }

    static void testNullCurrencyDoesNotChangePriorState() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        dfs.setCurrency(Currency.getInstance("USD"));
        try {
            dfs.setCurrency(null);
            throw new RuntimeException("Expected NullPointerException for null currency");
        } catch (NullPointerException e) {
            // expected
        }
        if (!"USD".equals(dfs.getInternationalCurrencySymbol())) {
            throw new RuntimeException("State changed after rejected null currency: "
                    + dfs.getInternationalCurrencySymbol());
        }
    }
}
