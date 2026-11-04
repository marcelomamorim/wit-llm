/*
 * @test
 * @summary Verifies observable behavior of BreakIteratorProviderImpl.getWordInstance(Locale)
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Set<String> langtags = new HashSet<String>();
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        BreakIterator bi = provider.getWordInstance(Locale.US);
        check(bi != null, "getWordInstance returned null");

        String text = "Hello world";
        bi.setText(text);
        check(bi.first() == 0, "first() should be 0");
        check(bi.next() == 5, "first word boundary should be 5");
        check(bi.next() == 6, "second word boundary should be 6");
        check(bi.next() == 11, "third word boundary should be 11");
        check(bi.next() == BreakIterator.DONE, "iterator should be exhausted");

        boolean npeThrown = false;
        try {
            provider.getWordInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null locale should throw NullPointerException");
    }
}
