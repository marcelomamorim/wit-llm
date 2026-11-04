/*
 * @test
 * @summary Verifies that getSentenceInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(null, new HashSet<String>());
        boolean thrown = false;
        try {
            provider.getSentenceInstance((Locale) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }
}
