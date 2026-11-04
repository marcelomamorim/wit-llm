/* @test    @summary Test getWordInstance with valid and invalid locales    @run main BreakIteratorProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetWordInstanceWithValidLocale();
        testGetWordInstanceWithNullLocale();
        testGetWordInstanceWithUnsupportedLocale();
    }

    static void testGetWordInstanceWithValidLocale() {
        try {
            BreakIterator bi = new BreakIteratorProviderImpl(
                null, null
            ).getWordInstance(Locale.ENGLISH);
            if (bi == null) {
                throw new AssertionError("Expected non-null BreakIterator for Locale.ENGLISH");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid locale: " + e);
        }
    }

    static void testGetWordInstanceWithNullLocale() {
        try {
            BreakIterator bi = new BreakIteratorProviderImpl(
                null, null
            ).getWordInstance(null);
            if (bi != null) {
                throw new AssertionError("Expected exception or null for null locale");
            }
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null locale: " + e);
        }
    }

    static void testGetWordInstanceWithUnsupportedLocale() {
        try {
            Locale unsupportedLocale = new Locale("xx", "YY");
            BreakIterator bi = new BreakIteratorProviderImpl(
                null, null
            ).getWordInstance(unsupportedLocale);
            if (bi == null) {
                throw new AssertionError("Expected non-null BreakIterator for unsupported locale");
            }
        } catch (IllegalArgumentException e) {
            // Expected if locale not supported
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for unsupported locale: " + e);
        }
    }
}
