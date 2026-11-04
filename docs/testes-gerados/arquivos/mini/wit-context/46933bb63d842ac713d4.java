/*
 * @test
 * @summary Test LocaleNameProvider.getDisplayUnicodeExtensionKey for null argument checks and default return
 * @run main java.util.spi.LocaleNameProviderWitupTest
 */

import java.util.Locale;
import java.util.spi.LocaleNameProvider;
import java.util.Objects;

public class LocaleNameProviderWitupTest extends LocaleNameProvider {

    public LocaleNameProviderWitupTest() {
        super();
    }

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

    public static void main(String[] args) {
        LocaleNameProviderWitupTest provider = new LocaleNameProviderWitupTest();

        // Test 1: key == null should throw NullPointerException
        try {
            provider.getDisplayUnicodeExtensionKey(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: locale == null should throw NullPointerException
        try {
            provider.getDisplayUnicodeExtensionKey("key", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 3: valid non-null arguments should return null (default implementation)
        String result = provider.getDisplayUnicodeExtensionKey("key", Locale.US);
        if (result != null) {
            throw new RuntimeException("Expected null return for valid inputs");
        }
    }

    @Override
    public java.util.Locale[] getAvailableLocales() {
        return new java.util.Locale[] { Locale.US };
    }

    @Override
    public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(locale);
        return null;
    }
}
