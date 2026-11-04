/*
 * @test
 * @summary Test sun.util.locale.provider.CurrencyNameProviderImpl.getSymbol(String, Locale) for expected behavior and exceptions
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CurrencyNameProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup: create a CurrencyNameProviderImpl with a known locale tag
        Set<String> langtags = Set.of("en-US", "fr-FR");
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test 1: getSymbol with valid currency code and supported locale
        Locale localeUS = Locale.forLanguageTag("en-US");
        String symbolUS = provider.getSymbol("usd", localeUS);
        if (symbolUS == null) {
            throw new AssertionError("Expected non-null symbol for USD and en-US locale");
        }

        // Test 2: getSymbol with valid currency code and unsupported locale returns null or throws
        Locale localeUnsupported = Locale.forLanguageTag("de-DE");
        try {
            String symbolUnsupported = provider.getSymbol("usd", localeUnsupported);
            if (symbolUnsupported != null) {
                throw new AssertionError("Expected null symbol for unsupported locale de-DE");
            }
        } catch (IllegalArgumentException e) {
            // Acceptable: locale not in getAvailableLocales()
        }

        // Test 3: getSymbol with null locale throws NullPointerException
        try {
            provider.getSymbol("usd", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }

        // Test 4: getSymbol with null currencyCode throws NullPointerException
        try {
            provider.getSymbol(null, localeUS);
            throw new AssertionError("Expected NullPointerException for null currencyCode");
        } catch (NullPointerException expected) {
        }

        // Test 5: getSymbol with invalid currencyCode format throws IllegalArgumentException or returns null
        try {
            String symbolInvalid = provider.getSymbol("usd1", localeUS);
            if (symbolInvalid != null) {
                throw new AssertionError("Expected null or exception for invalid currencyCode format");
            }
        } catch (IllegalArgumentException e) {
            // Acceptable
        }

        System.out.println("All tests passed.");
    }
}
