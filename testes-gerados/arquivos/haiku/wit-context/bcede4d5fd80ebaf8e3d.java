/*
 * @test
 * @summary Verifies ResourceBundle.Control.getFallbackLocale for the no-fallback control: NPE on null arguments and null return otherwise
 * @modules java.base
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectNpe(Runnable r, String msg) {
        try {
            r.run();
            check(false, msg + " expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    public static void main(String[] args) {
        ResourceBundle.Control control =
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);

        expectNpe(() -> control.getFallbackLocale(null, Locale.FRENCH),
                  "null baseName");
        expectNpe(() -> control.getFallbackLocale("base", null),
                  "null locale");
        expectNpe(() -> control.getFallbackLocale(null, null),
                  "null baseName and locale");

        check(control.getFallbackLocale("base", Locale.FRENCH) == null,
              "non-null arguments should yield null fallback");
        check(control.getFallbackLocale("base", Locale.ROOT) == null,
              "ROOT locale should yield null fallback");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
