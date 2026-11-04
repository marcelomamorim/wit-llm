/*
 * @test
 * @summary Checks ResourceBundle.Control.getTimeToLive null guards and default return value
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        ResourceBundle.Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        checkNullBaseName(control);
        checkNullLocale(control);
        checkNonNullReturnsValue(control);
    }

    static void checkNullBaseName(ResourceBundle.Control control) {
        boolean thrown = false;
        try {
            control.getTimeToLive(null, Locale.ROOT);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null baseName");
        }
    }

    static void checkNullLocale(ResourceBundle.Control control) {
        boolean thrown = false;
        try {
            control.getTimeToLive("base", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null locale");
        }
    }

    static void checkNonNullReturnsValue(ResourceBundle.Control control) {
        long ttl = control.getTimeToLive("base", Locale.ROOT);
        if (ttl != -2L) {
            throw new RuntimeException("unexpected TTL: " + ttl);
        }
    }
}
