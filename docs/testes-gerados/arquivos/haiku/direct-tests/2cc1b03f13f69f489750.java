/*
 * @test
 * @summary Verifies that BreakIteratorProviderImpl.getLineInstance returns a usable, fresh line BreakIterator
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.text.BreakIterator;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());

        testReturnsNonNullInstance(provider);
        testReturnsNewInstanceEachCall(provider);
        testLineBoundaryBehavior(provider);
        testNullLocaleThrowsNPE(provider);
    }

    static void testReturnsNonNullInstance(BreakIteratorProviderImpl provider) {
        BreakIterator bi = provider.getLineInstance(Locale.US);
        if (bi == null) {
            throw new RuntimeException("getLineInstance returned null");
        }
    }

    static void testReturnsNewInstanceEachCall(BreakIteratorProviderImpl provider) {
        BreakIterator first = provider.getLineInstance(Locale.US);
        BreakIterator second = provider.getLineInstance(Locale.US);
        if (first == second) {
            throw new RuntimeException("getLineInstance returned the same instance twice");
        }
    }

    static void testLineBoundaryBehavior(BreakIteratorProviderImpl provider) {
        BreakIterator bi = provider.getLineInstance(Locale.US);
        String text = "Hello world";
        bi.setText(text);
        int first = bi.first();
        if (first != 0) {
            throw new RuntimeException("first() expected 0 but was " + first);
        }
        int next = bi.following(0);
        if (next != 6) {
            throw new RuntimeException("following(0) expected 6 but was " + next);
        }
        int last = bi.last();
        if (last != text.length()) {
            throw new RuntimeException("last() expected " + text.length() + " but was " + last);
        }
    }

    static void testNullLocaleThrowsNPE(BreakIteratorProviderImpl provider) {
        boolean thrown = false;
        try {
            provider.getLineInstance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getLineInstance(null) did not throw NullPointerException");
        }
    }
}
