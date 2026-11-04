/*
 * @test
 * @summary WITUP-derived test for BreakIteratorProviderImpl.getLineInstance: null locale must throw NPE and valid locale must yield a working line break iterator
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.values()[0];
        Set<String> langtags = Collections.emptySet();
        BreakIteratorProviderImpl impl = new BreakIteratorProviderImpl(type, langtags);

        boolean npeThrown = false;
        try {
            impl.getLineInstance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }

        BreakIterator bi = impl.getLineInstance(Locale.US);
        if (bi == null) {
            throw new RuntimeException("getLineInstance returned null");
        }
        String text = "abc def";
        bi.setText(text);
        if (bi.first() != 0) {
            throw new RuntimeException("first boundary should be 0");
        }
        if (bi.next() != 4) {
            throw new RuntimeException("line boundary after 'abc ' should be 4");
        }
        if (bi.last() != text.length()) {
            throw new RuntimeException("last boundary should be text length");
        }

        BreakIterator other = impl.getLineInstance(Locale.US);
        if (other == bi) {
            throw new RuntimeException("getLineInstance should return a new instance");
        }
    }
}
