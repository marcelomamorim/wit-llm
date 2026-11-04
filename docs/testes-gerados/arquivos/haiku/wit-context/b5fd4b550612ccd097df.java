/*
 * @test
 * @summary ResourceBundle.Control.getFallbackLocale: NPE for null baseName and default-locale fallback behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    static class TestControl extends ResourceBundle.Control {
    }

    public static void main(String[] args) {
        ResourceBundle.Control control = new TestControl();

        boolean npeThrown = false;
        try {
            control.getFallbackLocale(null, Locale.ROOT);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null baseName");
        }

        Locale defaultLocale = Locale.getDefault();
        Locale result = control.getFallbackLocale("base", defaultLocale);
        if (result != null) {
            throw new RuntimeException("Expected null when locale equals default, got " + result);
        }

        Locale other = defaultLocale.equals(Locale.ROOT) ? Locale.FRENCH : Locale.ROOT;
        Locale fallback = control.getFallbackLocale("base", other);
        if (!defaultLocale.equals(fallback)) {
            throw new RuntimeException("Expected default locale " + defaultLocale + " but got " + fallback);
        }
    }
}
