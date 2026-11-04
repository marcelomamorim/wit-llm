/*
 * @test
 * @summary Test BreakIteratorProviderImpl.getCharacterInstance(Locale) for null and valid locale
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        testGetCharacterInstanceWithNullLocale();
        testGetCharacterInstanceWithValidLocale();
    }

    private static void testGetCharacterInstanceWithNullLocale() {
        // Setup with dummy type and empty langtags
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());
        try {
            provider.getCharacterInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetCharacterInstanceWithValidLocale() {
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale locale = Locale.ENGLISH;
        BreakIterator bi = provider.getCharacterInstance(locale);
        if (bi == null) {
            throw new RuntimeException("getCharacterInstance returned null for valid locale");
        }
    }
}
