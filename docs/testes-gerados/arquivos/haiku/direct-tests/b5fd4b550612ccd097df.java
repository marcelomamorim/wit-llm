/*
 * @test
 * @summary Verifies ResourceBundle.getFallbackLocale returns null for the default locale, the default locale otherwise, and throws NullPointerException for a null baseName
 * @run main ResourceBundleWitupTest
 */

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    static class TestBundle extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return java.util.Collections.emptyEnumeration();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Locale originalDefault = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            TestBundle bundle = new TestBundle();

            Locale fallback = bundle.getFallbackLocale("base", Locale.US);
            check(fallback == null, "Expected null when locale equals default, got " + fallback);

            fallback = bundle.getFallbackLocale("base", Locale.FRANCE);
            check(Locale.US.equals(fallback), "Expected default locale US, got " + fallback);

            Locale.setDefault(Locale.FRANCE);
            fallback = bundle.getFallbackLocale("base", Locale.FRANCE);
            check(fallback == null, "Expected null after changing default to FRANCE, got " + fallback);

            fallback = bundle.getFallbackLocale("base", Locale.US);
            check(Locale.FRANCE.equals(fallback), "Expected default locale FRANCE, got " + fallback);

            boolean thrown = false;
            try {
                bundle.getFallbackLocale(null, Locale.US);
            } catch (NullPointerException e) {
                thrown = true;
            }
            check(thrown, "Expected NullPointerException for null baseName");
        } finally {
            Locale.setDefault(originalDefault);
        }
    }
}
