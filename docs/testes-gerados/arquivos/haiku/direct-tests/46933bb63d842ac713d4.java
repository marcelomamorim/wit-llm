/*
 * @test
 * @summary Verifies null-argument checks and default null return of LocaleNameProvider.getDisplayUnicodeExtensionKey
 * @run main LocaleNameProviderWitupTest
 */

import java.util.Locale;
import java.util.spi.LocaleNameProvider;

public class LocaleNameProviderWitupTest {

    static class StubProvider extends LocaleNameProvider {
        @Override
        public String getDisplayLanguage(String languageCode, Locale locale) {
            return null;
        }

        @Override
        public String getDisplayScript(String scriptCode, Locale locale) {
            return null;
        }

        @Override
        public String getDisplayCountry(String countryCode, Locale locale) {
            return null;
        }

        @Override
        public String getDisplayVariant(String variant, Locale locale) {
            return null;
        }

        @Override
        public Locale[] getAvailableLocales() {
            return new Locale[] { Locale.US };
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        StubProvider p = new StubProvider();

        String r = p.getDisplayUnicodeExtensionKey("co", Locale.US);
        check(r == null, "expected null for non-null arguments, got " + r);

        r = p.getDisplayUnicodeExtensionKey("nu", Locale.FRANCE);
        check(r == null, "expected null for different locale, got " + r);

        try {
            p.getDisplayUnicodeExtensionKey(null, Locale.US);
            throw new RuntimeException("NullPointerException expected for null key");
        } catch (NullPointerException expected) {
        }

        try {
            p.getDisplayUnicodeExtensionKey("co", null);
            throw new RuntimeException("NullPointerException expected for null locale");
        } catch (NullPointerException expected) {
        }

        try {
            p.getDisplayUnicodeExtensionKey(null, null);
            throw new RuntimeException("NullPointerException expected for both null");
        } catch (NullPointerException expected) {
        }
    }
}
