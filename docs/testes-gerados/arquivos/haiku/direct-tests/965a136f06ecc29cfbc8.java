/*
 * @test
 * @summary Verifies that BreakIteratorProviderImpl.getCharacterInstance returns a working character break iterator
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = new HashSet<>(Arrays.asList("en-US"));
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        BreakIterator bi = provider.getCharacterInstance(Locale.US);
        check(bi != null, "getCharacterInstance returned null");

        bi.setText("ab");
        check(bi.first() == 0, "first boundary should be 0");
        check(bi.next() == 1, "second boundary should be 1");
        check(bi.next() == 2, "third boundary should be 2");
        check(bi.next() == BreakIterator.DONE, "iterator should be exhausted");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
