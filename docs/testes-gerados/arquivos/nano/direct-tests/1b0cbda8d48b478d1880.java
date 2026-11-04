/* @test    @summary Test the static method DecimalStyle.of(Locale) for different locales    @run main DecimalStyleWitupTest */

import java.time.format.DecimalStyle;
import java.util.Locale;

public class DecimalStyleWitupTest {

    public static void main(String[] args) {
        testDefaultLocale();
        testUSLocale();
        testFranceLocale();
        testNullLocale();
    }

    static void testDefaultLocale() {
        try {
            DecimalStyle style = DecimalStyle.of(Locale.getDefault());
            assert style != null : "DecimalStyle.of(Locale) returned null for default locale";
        } catch (Exception e) {
            throw new AssertionError("Exception in testDefaultLocale", e);
        }
    }

    static void testUSLocale() {
        try {
            Locale us = Locale.US;
            DecimalStyle style = DecimalStyle.of(us);
            assert style != null : "DecimalStyle.of(Locale.US) returned null";
        } catch (Exception e) {
            throw new AssertionError("Exception in testUSLocale", e);
        }
    }

    static void testFranceLocale() {
        try {
            Locale fr = Locale.FRANCE;
            DecimalStyle style = DecimalStyle.of(fr);
            assert style != null : "DecimalStyle.of(Locale.FRANCE) returned null";
        } catch (Exception e) {
            throw new AssertionError("Exception in testFranceLocale", e);
        }
    }

    static void testNullLocale() {
        try {
            DecimalStyle.of(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
