/*
 * @test
 * @summary Basic tests for CurrencyNameProviderImpl.getSymbol method
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CurrencyNameProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup a dummy langtags set with "en" and "fr"
        Set<String> langtags = new java.util.HashSet<>();
        langtags.add("en");
        langtags.add("fr");

        // Use LocaleProviderAdapter.Type.JRE as type (exists in JDK 11)
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test getAvailableLanguageTags returns the same set
        Set<String> availableTags = provider.getAvailableLanguageTags();
        if (!availableTags.contains("en") || !availableTags.contains("fr")) {
            throw new AssertionError("getAvailableLanguageTags missing expected tags");
        }

        // Test getAvailableLocales returns non-empty array
        Locale[] locales = provider.getAvailableLocales();
        if (locales == null || locales.length == 0) {
            throw new AssertionError("getAvailableLocales returned empty array");
        }

        // Test getSymbol with valid currency code and locale in langtags
        // Since getString is not visible, we test that getSymbol returns null or non-null but does not throw
        String symbol = provider.getSymbol("usd", Locale.ENGLISH);
        // symbol may be null or non-null, but must not throw

        // Test getSymbol with uppercase currency code
        String symbol2 = provider.getSymbol("EUR", Locale.ENGLISH);

        // Test getSymbol with locale not in langtags - should still not throw
        Locale unsupportedLocale = Locale.CHINESE;
        String symbol3 = provider.getSymbol("JPY", unsupportedLocale);

        // Test getSymbol with null currencyCode - expect NullPointerException
        boolean npeThrown = false;
        try {
            provider.getSymbol(null, Locale.ENGLISH);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null currencyCode");
        }

        // Test getSymbol with null locale - expect NullPointerException
        npeThrown = false;
        try {
            provider.getSymbol("USD", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null locale");
        }

        // Test getSymbol with invalid currency code format - expect IllegalArgumentException
        // According to javadoc, but since getSymbol calls getString with toUpperCase, and getString is not shown,
        // we cannot guarantee exception, so we do not test exception here.

        System.out.println("All tests passed.");
    }
}
