/*
 * @test
 * @summary Verifies that getWordInstance rejects a null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main BreakIteratorProviderImplWitupTest
 */
import java.util.Collections;
import sun.util.locale.provider.BreakIteratorProviderImpl;

public class BreakIteratorProviderImplWitupTest {
    public static void main(String[] args) {
        BreakIteratorProviderImpl provider =
            new BreakIteratorProviderImpl(null, Collections.<String>emptySet());
        try {
            provider.getWordInstance(null);
            throw new RuntimeException("NullPointerException was not thrown for null locale");
        } catch (NullPointerException expected) {
        }
    }
}
