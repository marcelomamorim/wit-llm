/*
 * @test
 * @summary Verifies ResourceBundle.Control.getTimeToLive null checks and default return value
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    private static final long EXPECTED_NO_EXPIRATION = -2L;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static ResourceBundle.Control newControl() {
        return new ResourceBundle.Control() {};
    }

    static void testNullBaseNameThrowsNPE() {
        ResourceBundle.Control control = newControl();
        boolean thrown = false;
        try {
            control.getTimeToLive(null, Locale.US);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null baseName");
    }

    static void testNullLocaleThrowsNPE() {
        ResourceBundle.Control control = newControl();
        boolean thrown = false;
        try {
            control.getTimeToLive("base", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null locale");
    }

    static void testBothNullThrowsNPE() {
        ResourceBundle.Control control = newControl();
        boolean thrown = false;
        try {
            control.getTimeToLive(null, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException when both arguments are null");
    }

    static void testValidArgumentsReturnNoExpiration() {
        ResourceBundle.Control control = newControl();
        long ttl = control.getTimeToLive("base", Locale.US);
        check(ttl == EXPECTED_NO_EXPIRATION,
              "expected no-expiration value, got " + ttl);
    }

    static void testDifferentLocalesReturnSameValue() {
        ResourceBundle.Control control = newControl();
        long a = control.getTimeToLive("base", Locale.US);
        long b = control.getTimeToLive("other.Bundle", Locale.ROOT);
        check(a == b && a == EXPECTED_NO_EXPIRATION,
              "expected consistent no-expiration value");
    }

    public static void main(String[] args) {
        testNullBaseNameThrowsNPE();
        testNullLocaleThrowsNPE();
        testBothNullThrowsNPE();
        testValidArgumentsReturnNoExpiration();
        testDifferentLocalesReturnSameValue();
    }
}
