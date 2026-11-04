/*
 * @test
 * @summary Test BreakIteratorProviderImpl.getWordInstance(Locale) for null and valid locale
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        testGetWordInstanceWithNullLocale();
        testGetWordInstanceWithValidLocale();
    }

    private static void testGetWordInstanceWithNullLocale() {
        // Setup with dummy type and empty langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(dummyType, new HashSet<String>());

        try {
            provider.getWordInstance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetWordInstanceWithValidLocale() {
        // Setup with dummy type and langtags containing en
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(dummyType, langtags);

        Locale locale = Locale.ENGLISH;
        BreakIterator bi = provider.getWordInstance(locale);
        if (bi == null) {
            throw new RuntimeException("getWordInstance returned null for valid locale");
        }
    }
}
