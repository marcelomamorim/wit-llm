/* @test    @summary Test for getSentenceInstance method in BreakIteratorProviderImpl to verify behavior with null locale    @run main BreakIteratorProviderImplWitupTest */

import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.util.Locale;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetSentenceInstanceNullLocale();
    }

    public static void testGetSentenceInstanceNullLocale() {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(null, null);
        try {
            BreakIterator result = provider.getSentenceInstance(null);
            // Expecting a NullPointerException, so if no exception, the test fails
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Test passes if NullPointerException is thrown
            System.out.println("NullPointerException correctly thrown for null locale.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
