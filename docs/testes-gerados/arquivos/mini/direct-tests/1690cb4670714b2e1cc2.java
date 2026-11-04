/*
 * @test
 * @summary Test BreakIteratorProviderImpl.getSentenceInstance(Locale) behavior
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a langtags set with "en" to support Locale.ENGLISH
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");

        // Use LocaleProviderAdapter.Type.JRE as dummy type (null safe for test)
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test getSentenceInstance with supported locale
        Locale locale = Locale.ENGLISH;
        BreakIterator bi = provider.getSentenceInstance(locale);
        if (bi == null) {
            throw new RuntimeException("getSentenceInstance returned null for supported locale");
        }

        // Test getSentenceInstance with unsupported locale
        Locale unsupported = Locale.FRENCH;
        try {
            provider.getSentenceInstance(unsupported);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getSentenceInstance with null locale
        try {
            provider.getSentenceInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        System.out.println("BreakIteratorProviderImplWitupTest passed");
    }
}
