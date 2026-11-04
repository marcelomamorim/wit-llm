/* @test    @summary Test getDisplayUnicodeExtensionKey method of LocaleNameProviderImpl    @run main LocaleNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        testGetDisplayUnicodeExtensionKey();
    }

    public static void testGetDisplayUnicodeExtensionKey() {
        Locale locale = Locale.US;
        String key = "example";
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
        String result = provider.getDisplayUnicodeExtensionKey(key, locale);
        String expected = "key.example";
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (!result.equals(expected) && !result.equals(key)) {
            throw new AssertionError("Expected: " + expected + " or " + key + ", but got: " + result);
        }
        // Test with a key that should return the key itself
        String unknownKey = "unknown";
        String result2 = provider.getDisplayUnicodeExtensionKey(unknownKey, locale);
        if (result2 == null) {
            throw new AssertionError("Result should not be null for unknown key");
        }
        if (!result2.equals("key." + unknownKey) && !result2.equals(unknownKey)) {
            throw new AssertionError("Expected: " + ("key." + unknownKey) + " or " + unknownKey + ", but got: " + result2);
        }
    }
}
