/* @test    @summary Test getPercentInstance() method of NumberFormatProviderImpl for various locales    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;
import sun.util.locale.provider.NumberFormatProviderImpl;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetPercentInstance(Locale.US);
        testGetPercentInstance(Locale.FRANCE);
        testGetPercentInstance(Locale.JAPAN);
        testGetPercentInstance(new Locale("xx", "YY"));
    }

    private static void testGetPercentInstance(Locale locale) {
        NumberFormat nf = new NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null)
                .getPercentInstance(locale);
        if (nf == null) {
            throw new AssertionError("getPercentInstance returned null for locale: " + locale);
        }
        if (!(nf instanceof NumberFormat)) {
            throw new AssertionError("Returned object is not an instance of NumberFormat for locale: " + locale);
        }
        String localeStr = locale.toString();
        System.out.println("getPercentInstance for locale: " + localeStr + " returned: " + nf.toString());
    }
}
