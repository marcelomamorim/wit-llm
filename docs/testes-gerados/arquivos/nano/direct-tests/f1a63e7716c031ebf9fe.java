/* @test    @summary Test getCurrencyInstance with valid and invalid locales    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetCurrencyInstanceWithSupportedLocale();
        testGetCurrencyInstanceWithUnsupportedLocale();
    }

    static void testGetCurrencyInstanceWithSupportedLocale() {
        Locale locale = Locale.US;
        NumberFormat nf = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getCurrencyInstance(locale);
        assert nf != null : "Expected non-null NumberFormat for supported locale";
        assert nf.getClass().getName().contains("NumberFormat") : "Expected NumberFormat instance";
    }

    static void testGetCurrencyInstanceWithUnsupportedLocale() {
        Locale locale = new Locale("xx", "XX");
        sun.util.locale.provider.NumberFormatProviderImpl provider = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        NumberFormat nf = provider.getCurrencyInstance(locale);
        assert nf != null : "Expected non-null NumberFormat even for unsupported locale";
        assert nf.getClass().getName().contains("NumberFormat") : "Expected NumberFormat instance";
    }
}
