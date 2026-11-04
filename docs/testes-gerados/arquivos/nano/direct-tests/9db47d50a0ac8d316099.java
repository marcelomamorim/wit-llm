/* @test    @summary Test getInstance with valid and null locales    @run main DateFormatSymbolsProviderImplWitupTest */

import java.text.DateFormatSymbols;
import java.util.Locale;
import sun.util.locale.provider.DateFormatSymbolsProviderImpl;

public class DateFormatSymbolsProviderImplWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithValidLocale();
        testGetInstanceWithNullLocale();
    }

    static void testGetInstanceWithValidLocale() {
        Locale locale = Locale.US;
        DateFormatSymbolsProviderImpl provider = new DateFormatSymbolsProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        DateFormatSymbols symbols = provider.getInstance(locale);
        assert symbols != null : "Expected non-null DateFormatSymbols";
        assert symbols.getLocale().equals(locale) : "Locale mismatch";
    }

    static void testGetInstanceWithNullLocale() {
        DateFormatSymbolsProviderImpl provider = new DateFormatSymbolsProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            provider.getInstance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
