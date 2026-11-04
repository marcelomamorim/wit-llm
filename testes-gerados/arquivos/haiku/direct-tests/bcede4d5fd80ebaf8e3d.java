/*
 * @test
 * @summary Verify ResourceBundle.getFallbackLocale throws NPE on null arguments and returns null otherwise
 * @run main ResourceBundleWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    static class Sub extends ResourceBundle {
        @Override
        protected Object handleGetObject(String key) {
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return Collections.emptyEnumeration();
        }
    }

    static void expectNPE(String baseName, Locale locale) {
        try {
            new Sub().getFallbackLocale(baseName, locale);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for baseName="
                + baseName + " locale=" + locale);
    }

    public static void main(String[] args) {
        Sub bundle = new Sub();

        if (bundle.getFallbackLocale("base", Locale.US) != null) {
            throw new RuntimeException("Expected null fallback for Locale.US");
        }
        if (bundle.getFallbackLocale("base", Locale.ROOT) != null) {
            throw new RuntimeException("Expected null fallback for Locale.ROOT");
        }
        if (bundle.getFallbackLocale("other.base", Locale.FRENCH) != null) {
            throw new RuntimeException("Expected null fallback for Locale.FRENCH");
        }

        expectNPE(null, Locale.US);
        expectNPE("base", null);
        expectNPE(null, null);
    }
}
