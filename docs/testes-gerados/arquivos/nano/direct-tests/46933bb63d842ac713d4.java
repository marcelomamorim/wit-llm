/* @test    @summary Test getDisplayUnicodeExtensionKey method of LocaleNameProvider    @run main LocaleNameProviderWitupTest */

import java.util.Locale;
import java.util.spi.LocaleNameProvider;

public class LocaleNameProviderWitupTest {

    public static void main(String[] args) {
        testGetDisplayUnicodeExtensionKeyReturnsNullForAnyInput();
        testGetDisplayUnicodeExtensionKeyNullKeyThrows();
        testGetDisplayUnicodeExtensionKeyNullLocaleThrows();
    }

    static void testGetDisplayUnicodeExtensionKeyReturnsNullForAnyInput() {
        LocaleNameProvider provider = new LocaleNameProvider() {
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
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                return null;
            }
        };
        String key = "testKey";
        Locale locale = Locale.US;
        String result = provider.getDisplayUnicodeExtensionKey(key, locale);
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
    }

    static void testGetDisplayUnicodeExtensionKeyNullKeyThrows() {
        LocaleNameProvider provider = new LocaleNameProvider() {
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
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                return null;
            }
        };
        boolean thrown = false;
        try {
            provider.getDisplayUnicodeExtensionKey(null, Locale.US);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null key");
        }
    }

    static void testGetDisplayUnicodeExtensionKeyNullLocaleThrows() {
        LocaleNameProvider provider = new LocaleNameProvider() {
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
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                return null;
            }
        };
        boolean thrown = false;
        try {
            provider.getDisplayUnicodeExtensionKey("testKey", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null locale");
        }
    }
}
