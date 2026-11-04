/*
 * @test
 * @summary Test sun.util.locale.provider.BreakIteratorProviderImpl.getLineInstance(Locale)
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a BreakIteratorProviderImpl with a known locale tag
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test getLineInstance with a supported locale
        Locale enLocale = Locale.ENGLISH;
        BreakIterator bi = provider.getLineInstance(enLocale);
        if (bi == null) {
            throw new RuntimeException("getLineInstance returned null for supported locale");
        }

        // Test getLineInstance with a locale not in langtags - expect IllegalArgumentException
        Locale unsupported = Locale.FRENCH;
        try {
            provider.getLineInstance(unsupported);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test getLineInstance with null locale - expect NullPointerException
        try {
            provider.getLineInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
