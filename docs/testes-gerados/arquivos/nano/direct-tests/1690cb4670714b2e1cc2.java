/* @test    @summary Test getSentenceInstance method of BreakIteratorProviderImpl    @run main BreakIteratorProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetSentenceInstanceWithValidLocale();
        testGetSentenceInstanceWithNullLocale();
    }

    private static void testGetSentenceInstanceWithValidLocale() {
        Locale locale = Locale.ENGLISH;
        BreakIterator bi = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null)
                                .getSentenceInstance(locale);
        if (bi == null) {
            throw new AssertionError("BreakIterator should not be null for valid locale");
        }
        if (bi.getClass().getName().contains("BreakIterator")) {
            System.out.println("PASS: getSentenceInstance returns a BreakIterator for locale: " + locale);
        } else {
            throw new AssertionError("Unexpected BreakIterator class: " + bi.getClass().getName());
        }
    }

    private static void testGetSentenceInstanceWithNullLocale() {
        try {
            new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getSentenceInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: getSentenceInstance throws NullPointerException for null locale");
        }
    }
}
