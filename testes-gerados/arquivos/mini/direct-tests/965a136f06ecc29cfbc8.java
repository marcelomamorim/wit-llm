/*
 * @test
 * @summary Test BreakIteratorProviderImpl.getCharacterInstance(Locale) behavior
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetCharacterInstanceWithValidLocale();
        testGetCharacterInstanceWithNullLocale();
        testGetCharacterInstanceWithUnsupportedLocale();
    }

    private static void testGetCharacterInstanceWithValidLocale() {
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en-US");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale locale = Locale.forLanguageTag("en-US");
        BreakIterator bi = provider.getCharacterInstance(locale);
        if (bi == null) {
            throw new AssertionError("getCharacterInstance returned null for supported locale");
        }
        // Check that the BreakIterator is for character instance by testing first() and next()
        bi.setText("abc");
        int first = bi.first();
        int next = bi.next();
        if (first != 0 || next <= first) {
            throw new AssertionError("BreakIterator behavior unexpected for character instance");
        }
    }

    private static void testGetCharacterInstanceWithNullLocale() {
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en-US");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        try {
            provider.getCharacterInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        } catch (IllegalArgumentException iae) {
            // Some implementations may throw IllegalArgumentException instead
            // Acceptable as well
        }
    }

    private static void testGetCharacterInstanceWithUnsupportedLocale() {
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en-US");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale unsupportedLocale = Locale.forLanguageTag("fr-FR");
        try {
            provider.getCharacterInstance(unsupportedLocale);
            throw new AssertionError("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (NullPointerException npe) {
            // Some implementations may throw NullPointerException if locale is null, but here locale is not null
            throw new AssertionError("Unexpected NullPointerException for non-null unsupported locale");
        }
    }
}
