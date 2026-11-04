/*
 * @test
 * @summary Test for getCharacterInstance method in BreakIteratorProviderImpl
 * @run main BreakIteratorProviderImplWitupTest
 */

import sun.util.locale.provider.BreakIteratorProviderImpl;
import java.util.Locale;
import java.text.BreakIterator;

public class BreakIteratorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetCharacterInstanceWithNullLocale();
        testGetCharacterInstanceWithValidLocale();
    }

    private static void testGetCharacterInstanceWithNullLocale() {
        try {
            BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(null, null);
            provider.getCharacterInstance(null);
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testGetCharacterInstanceWithValidLocale() {
        try {
            Locale locale = Locale.US;
            BreakIteratorProviderImpl provider = new BreakIteratorProviderImpl(Locale.Provider.Type.LAYOUT, null);
            BreakIterator bi = provider.getCharacterInstance(locale);
            if (bi != null) {
                System.out.println("PASS: getCharacterInstance returned a BreakIterator for locale: " + locale);
            } else {
                System.out.println("FAIL: getCharacterInstance returned null for locale: " + locale);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
