/*
 * @test
 * @summary Test DecimalStyle.of(Locale) for null argument and caching behavior
 * @run main DecimalStyleWitupTest
 */

import java.time.format.DecimalStyle;
import java.util.Locale;

public class DecimalStyleWitupTest {
    public static void main(String[] args) {
        testNullLocaleThrows();
        testCachingSameInstance();
        testDifferentLocalesReturnDifferentInstances();
    }

    private static void testNullLocaleThrows() {
        try {
            DecimalStyle.of(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testCachingSameInstance() {
        Locale locale = Locale.US;
        DecimalStyle first = DecimalStyle.of(locale);
        DecimalStyle second = DecimalStyle.of(locale);
        if (first == null || second == null) {
            throw new AssertionError("DecimalStyle.of returned null");
        }
        if (first != second) {
            throw new AssertionError("DecimalStyle.of did not cache instance for same locale");
        }
    }

    private static void testDifferentLocalesReturnDifferentInstances() {
        Locale locale1 = Locale.US;
        Locale locale2 = Locale.FRANCE;
        DecimalStyle ds1 = DecimalStyle.of(locale1);
        DecimalStyle ds2 = DecimalStyle.of(locale2);
        if (ds1 == null || ds2 == null) {
            throw new AssertionError("DecimalStyle.of returned null");
        }
        if (ds1 == ds2) {
            throw new AssertionError("DecimalStyle.of returned same instance for different locales");
        }
    }
}
