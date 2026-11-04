/*
 * @test
 * @summary Test getDisplayUnicodeExtensionKey method for null checks and return values
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup with dummy type and empty langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, new HashSet<String>());

        // Test 1: key == null should throw NullPointerException
        try {
            provider.getDisplayUnicodeExtensionKey(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: locale == null and key != null should throw NullPointerException
        try {
            provider.getDisplayUnicodeExtensionKey("ca", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 3: valid key and locale returns expected string
        String key = "ca";
        Locale locale = Locale.ENGLISH;
        String result = provider.getDisplayUnicodeExtensionKey(key, locale);
        if (result == null) {
            throw new RuntimeException("Returned null for valid key and locale");
        }
        // The result is either the localized string or the key itself
        if (!result.equals(key) && !result.startsWith("key.")) {
            throw new RuntimeException("Returned unexpected string: " + result);
        }

        System.out.println("All tests passed.");
    }
}
