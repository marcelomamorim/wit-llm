/*
 * @test
 * @summary Test sun.util.locale.provider.BreakIteratorProviderImpl.getLineInstance(Locale) for null and valid locale
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        testGetLineInstanceNullLocale();
        testGetLineInstanceValidLocale();
    }

    private static void testGetLineInstanceNullLocale() {
        // Setup with dummy type and empty langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(dummyType, new HashSet<String>());

        try {
            provider.getLineInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetLineInstanceValidLocale() {
        // Setup with dummy type and langtags containing "en"
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en");
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(dummyType, langtags);

        Locale locale = Locale.ENGLISH;
        BreakIterator bi = provider.getLineInstance(locale);
        if (bi == null) {
            throw new RuntimeException("getLineInstance returned null for valid locale");
        }
        // Check that the returned BreakIterator is for line breaks by class name heuristic
        String className = bi.getClass().getName();
        if (!(className.contains("BreakIterator") || className.contains("DictionaryBasedBreakIterator") || className.contains("RuleBasedBreakIterator"))) {
            throw new RuntimeException("Returned BreakIterator instance class name unexpected: " + className);
        }
    }
}
