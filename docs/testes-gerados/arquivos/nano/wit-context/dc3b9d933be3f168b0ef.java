/* @test    @summary Test getNumberInstance with valid and null locale    @run main NumberFormatProviderImplWitupTest */

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatProviderImplWitupTest {

    public static void main(String[] args) {
        testGetNumberInstanceWithValidLocale();
        testGetNumberInstanceWithNullLocale();
    }

    private static void testGetNumberInstanceWithValidLocale() {
        try {
            Locale locale = Locale.US;
            NumberFormat nf = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getNumberInstance(locale);
            if (nf == null) {
                throw new AssertionError("Expected non-null NumberFormat for valid locale");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid locale: " + e);
        }
    }

    private static void testGetNumberInstanceWithNullLocale() {
        try {
            sun.util.locale.provider.NumberFormatProviderImpl provider = new sun.util.locale.provider.NumberFormatProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            provider.getNumberInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
