/*
 * @test
 * @summary Test BreakIteratorProviderImpl.getSentenceInstance(Locale) for null and valid locale
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        testGetSentenceInstanceWithNullLocale();
        testGetSentenceInstanceWithValidLocale();
    }

    private static void testGetSentenceInstanceWithNullLocale() {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, Collections.singleton("en"));
        try {
            provider.getSentenceInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetSentenceInstanceWithValidLocale() {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, Collections.singleton("en"));
        Locale locale = Locale.ENGLISH;
        BreakIterator bi = provider.getSentenceInstance(locale);
        if (bi == null) {
            throw new RuntimeException("getSentenceInstance returned null for valid locale");
        }
    }
}
