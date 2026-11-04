/* @test    @summary Test getLineInstance method of BreakIteratorProviderImpl    @run main BreakIteratorProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetLineInstanceWithValidLocale();
        testGetLineInstanceWithNullLocale();
    }

    private static void testGetLineInstanceWithValidLocale() {
        Locale locale = Locale.US;
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            BreakIterator bi = provider.getLineInstance(locale);
            if (bi == null) {
                throw new AssertionError("Expected non-null BreakIterator for locale: " + locale);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid locale: " + e);
        }
    }

    private static void testGetLineInstanceWithNullLocale() {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        try {
            provider.getLineInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
