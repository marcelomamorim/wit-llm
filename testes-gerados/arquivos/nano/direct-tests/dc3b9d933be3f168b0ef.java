/* @test    @summary Test getNumberInstance returns a NumberFormat for given locales    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetNumberInstance(Locale.US);
        testGetNumberInstance(Locale.FRANCE);
        testGetNumberInstance(Locale.JAPAN);
        testGetNumberInstance(new Locale("xx", "YY"));
    }

    private static void testGetNumberInstance(Locale locale) {
        try {
            NumberFormat nf = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getNumberInstance(locale);
            if (nf == null) {
                throw new AssertionError("getNumberInstance returned null for locale: " + locale);
            }
            String formatted = nf.format(12345.67);
            System.out.println("Locale: " + locale + ", formatted number: " + formatted);
        } catch (Exception e) {
            throw new AssertionError("Exception for locale: " + locale, e);
        }
    }
}
