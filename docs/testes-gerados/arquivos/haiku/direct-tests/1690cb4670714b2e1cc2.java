/*
 * @test
 * @summary Verifies BreakIteratorProviderImpl.getSentenceInstance returns sentence break iterators
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(null, Collections.<String>emptySet());

        BreakIterator first = provider.getSentenceInstance(Locale.US);
        if (first == null) {
            throw new RuntimeException("getSentenceInstance returned null");
        }

        BreakIterator second = provider.getSentenceInstance(Locale.US);
        if (first == second) {
            throw new RuntimeException("getSentenceInstance should return a new instance");
        }

        String text = "Hello world. How are you? Fine.";
        first.setText(text);
        int b1 = first.following(0);
        if (b1 != 13) {
            throw new RuntimeException("Expected first sentence boundary 13 but was " + b1);
        }
        int last = first.last();
        if (last != text.length()) {
            throw new RuntimeException("Expected last boundary " + text.length() + " but was " + last);
        }
    }
}
