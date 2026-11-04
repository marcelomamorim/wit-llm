/* @test    @summary Test getIntegerInstance with valid and invalid locales    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetIntegerInstanceWithSupportedLocale();
        testGetIntegerInstanceWithUnsupportedLocale();
    }

    static void testGetIntegerInstanceWithSupportedLocale() {
        try {
            Locale locale = Locale.US;
            NumberFormat nf = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getIntegerInstance(locale);
            if (nf == null) {
                throw new AssertionError("Expected non-null NumberFormat for supported locale") ;
            }
            if (nf.getClass().getName().contains("NumberFormat")) {
                System.out.println("PASS: getIntegerInstance returns NumberFormat for supported locale") ;
            } else {
                throw new AssertionError("Unexpected class for NumberFormat") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testGetIntegerInstanceWithSupportedLocale: " + e);
        }
    }

    static void testGetIntegerInstanceWithUnsupportedLocale() {
        try {
            Locale locale = new Locale("xx", "YY");
            sun.util.locale.provider.NumberFormatProviderImpl provider = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            NumberFormat nf = provider.getIntegerInstance(locale);
            if (nf == null) {
                System.out.println("PASS: getIntegerInstance returns null for unsupported locale") ;
            } else {
                System.out.println("INFO: getIntegerInstance returned non-null for unsupported locale") ;
            }
        } catch (Exception e) {
            throw new AssertionError("Exception in testGetIntegerInstanceWithUnsupportedLocale: " + e);
        }
    }
}
