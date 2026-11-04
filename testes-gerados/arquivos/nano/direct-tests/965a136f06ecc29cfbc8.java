/* @test    @summary Test getCharacterInstance returns a BreakIterator for given locale    @run main BreakIteratorProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetCharacterInstanceWithValidLocale();
        testGetCharacterInstanceWithNullLocale();
    }

    static void testGetCharacterInstanceWithValidLocale() {
        Locale locale = Locale.US;
        BreakIterator bi = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null)
                                .getCharacterInstance(locale);
        if (bi == null) {
            throw new AssertionError("BreakIterator should not be null for locale: " + locale);
        }
        if (!bi.getClass().getName().contains("BreakIterator")) {
            throw new AssertionError("Expected BreakIterator instance, got: " + bi.getClass().getName());
        }
        if (!bi.getLocale().equals(locale)) {
            throw new AssertionError("Locale mismatch: expected " + locale + ", got " + bi.getLocale());
        }
    }

    static void testGetCharacterInstanceWithNullLocale() {
        try {
            new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getCharacterInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
