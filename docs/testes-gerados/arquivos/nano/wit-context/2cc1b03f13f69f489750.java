/*
 * @test
 * @summary Test for BreakIteratorProviderImpl.getLineInstance(Locale)
 * @run main BreakIteratorProviderImplWitupTest
 */

import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.util.Locale;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetLineInstanceWithNullLocale();
        testGetLineInstanceWithValidLocale();
    }

    private static void testGetLineInstanceWithNullLocale() {
        try {
            BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(null, null);
            provider.getLineInstance(null);
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testGetLineInstanceWithValidLocale() {
        try {
            Locale locale = Locale.US;
            BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(Locale.Provider.Type.LAYOUT, null);
            BreakIterator bi = provider.getLineInstance(locale);
            if (bi != null && bi instanceof sun.text.RuleBasedBreakIterator) {
                System.out.println("PASS: getLineInstance returned a valid BreakIterator");
            } else {
                System.out.println("FAIL: getLineInstance did not return expected BreakIterator");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
