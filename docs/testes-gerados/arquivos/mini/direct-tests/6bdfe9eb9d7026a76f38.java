/*
 * @test
 * @summary Test getWordInstance(Locale) method of BreakIteratorProviderImpl
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare a set of language tags including English
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");

        // Create instance with Type.JRE (use JRE as example)
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test getAvailableLocales returns expected locales
        Locale[] available = provider.getAvailableLocales();
        boolean foundEn = false;
        for (Locale loc : available) {
            if ("en".equals(loc.getLanguage())) {
                foundEn = true;
                break;
            }
        }
        if (!foundEn) {
            throw new AssertionError("Locale 'en' not found in available locales");
        }

        // Test getWordInstance with a supported locale
        Locale enLocale = Locale.ENGLISH;
        BreakIterator bi = provider.getWordInstance(enLocale);
        if (bi == null) {
            throw new AssertionError("getWordInstance returned null for supported locale");
        }

        // Test getWordInstance with null locale throws NullPointerException
        try {
            provider.getWordInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test getWordInstance with unsupported locale throws IllegalArgumentException
        Locale unsupported = new Locale("zz", "ZZ");
        try {
            provider.getWordInstance(unsupported);
            throw new AssertionError("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
