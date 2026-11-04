/* @test    @summary Test getInstance with valid and null locales    @run main DecimalFormatSymbolsProviderImplWitupTest */

import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DecimalFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithValidLocale();
        testGetInstanceWithNullLocale();
    }

    static void testGetInstanceWithValidLocale() {
        try {
            Locale locale = Locale.US;
            sun.util.locale.provider.DecimalFormatSymbolsProviderImpl provider =
                new sun.util.locale.provider.DecimalFormatSymbolsProviderImpl(
                    sun.util.locale.provider.LocaleProviderAdapter.Type.DEFAULT,
                    java.util.Collections.singleton("en-US")
                );
            DecimalFormatSymbols symbols = provider.getInstance(locale);
            if (symbols == null) {
                throw new AssertionError("getInstance returned null for valid locale");
            }
            if (!symbols.getLocale().equals(locale)) {
                throw new AssertionError("Locale mismatch in DecimalFormatSymbols");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testGetInstanceWithValidLocale: " + e);
        }
    }

    static void testGetInstanceWithNullLocale() {
        try {
            sun.util.locale.provider.DecimalFormatSymbolsProviderImpl provider =
                new sun.util.locale.provider.DecimalFormatSymbolsProviderImpl(
                    sun.util.locale.provider.LocaleProviderAdapter.Type.DEFAULT,
                    java.util.Collections.singleton("en-US")
                );
            provider.getInstance(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
