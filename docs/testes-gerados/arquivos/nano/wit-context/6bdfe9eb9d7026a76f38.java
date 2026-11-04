/* @test    @summary Test for BreakIteratorProviderImpl.getWordInstance(Locale) handling null input    @run main BreakIteratorProviderImplWitupTest */

import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.text.BreakIterator;
import java.util.Locale;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetWordInstanceWithNullLocale();
    }

    public static void testGetWordInstanceWithNullLocale() {
        BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(null, null);
        try {
            BreakIterator bi = provider.getWordInstance(null);
            // Expecting a NullPointerException, so if we reach here, the test fails
            System.err.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Test passes if exception is thrown
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception type: " + e);
        }
    }
}
