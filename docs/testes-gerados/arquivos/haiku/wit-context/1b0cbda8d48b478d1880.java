/*
 * @test
 * @summary Verify DecimalStyle.of(Locale) rejects null and returns a cached non-null instance for a locale
 * @run main DecimalStyleWitupTest
 */

import java.time.format.DecimalStyle;
import java.util.Locale;

public class DecimalStyleWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNpe();
        testNonNullLocaleReturnsInstance();
        testSameLocaleReturnsSameInstance();
        System.out.println("PASS");
    }

    static void testNullLocaleThrowsNpe() {
        try {
            DecimalStyle.of(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null locale");
    }

    static void testNonNullLocaleReturnsInstance() {
        DecimalStyle style = DecimalStyle.of(Locale.US);
        if (style == null) {
            throw new RuntimeException("DecimalStyle.of(Locale.US) returned null");
        }
    }

    static void testSameLocaleReturnsSameInstance() {
        DecimalStyle first = DecimalStyle.of(Locale.US);
        DecimalStyle second = DecimalStyle.of(Locale.US);
        if (first != second) {
            throw new RuntimeException("DecimalStyle.of(Locale.US) did not return the cached instance");
        }
    }
}
