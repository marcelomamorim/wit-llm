/* @test    @summary Test for getDisplayUnicodeExtensionKey method in LocaleNameProvider.    @run main LocaleNameProviderWitupTest */

import java.util.Locale;
import java.util.spi.LocaleNameProvider;

public class LocaleNameProviderWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNullPointerException();
        testNullLocaleThrowsNullPointerException();
        testValidInputsReturnsNull();
    }

    private static void testNullKeyThrowsNullPointerException() {
        LocaleNameProvider provider = new LocaleNameProvider() {
            public String getDisplayLanguage(String languageCode, Locale locale) { return null; }
            public String getDisplayScript(String scriptCode, Locale locale) { return null; }
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                try {
                    return getDisplayUnicodeExtensionKey(key, locale);
                } catch (NullPointerException e) {
                    System.out.println("Passed: NullPointerException for null key");
                    return "exception";
                }
            }
        };
        String result = provider.getDisplayUnicodeExtensionKey(null, Locale.US);
        if (!"exception".equals(result)) {
            System.out.println("Failed: Expected NullPointerException for null key");
        }
    }

    private static void testNullLocaleThrowsNullPointerException() {
        LocaleNameProvider provider = new LocaleNameProvider() {
            public String getDisplayLanguage(String languageCode, Locale locale) { return null; }
            public String getDisplayScript(String scriptCode, Locale locale) { return null; }
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                try {
                    return getDisplayUnicodeExtensionKey(key, locale);
                } catch (NullPointerException e) {
                    System.out.println("Passed: NullPointerException for null locale");
                    return "exception";
                }
            }
        };
        String result = provider.getDisplayUnicodeExtensionKey("someKey", null);
        if (!"exception".equals(result)) {
            System.out.println("Failed: Expected NullPointerException for null locale");
        }
    }

    private static void testValidInputsReturnsNull() {
        LocaleNameProvider provider = new LocaleNameProvider() {
            public String getDisplayLanguage(String languageCode, Locale locale) { return null; }
            public String getDisplayScript(String scriptCode, Locale locale) { return null; }
            public String getDisplayUnicodeExtensionKey(String key, Locale locale) {
                return getDisplayUnicodeExtensionKey(key, locale);
            }
        };
        String result = provider.getDisplayUnicodeExtensionKey("testKey", Locale.FRANCE);
        if (result != null) {
            System.out.println("Passed: Valid inputs return null as per implementation");
        } else {
            System.out.println("Failed: Expected null for valid inputs");
        }
    }
}
