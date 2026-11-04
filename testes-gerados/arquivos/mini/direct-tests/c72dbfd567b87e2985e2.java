/*
 * @test
 * @summary Test getDisplayUnicodeExtensionKey method of LocaleNameProviderImpl
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        // Setup a LocaleNameProviderImpl with a dummy type and langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> dummyLangtags = Set.of("en", "fr");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, dummyLangtags);

        // Test 1: Normal key that maps to a display string different from the key
        // We expect getDisplayString to return a localized string different from "key." + key
        // Since getDisplayString is internal, we test the observable behavior:
        // If getDisplayString returns the same as rbKey, method returns key; else returns name

        // Use a key that likely has no mapping, so getDisplayString returns rbKey
        String key1 = "unknownkey";
        Locale localeEn = Locale.ENGLISH;
        String result1 = provider.getDisplayUnicodeExtensionKey(key1, localeEn);
        if (!result1.equals(key1)) {
            throw new AssertionError("Expected key to be returned when no display string found, got: " + result1);
        }

        // Test 2: Null key should throw NullPointerException due to super call
        try {
            provider.getDisplayUnicodeExtensionKey(null, localeEn);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 3: Null locale should throw NullPointerException due to super call
        try {
            provider.getDisplayUnicodeExtensionKey("key", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test 4: Key with known prefix "key." to check behavior
        String key2 = "calendar"; // common Unicode extension key
        String result2 = provider.getDisplayUnicodeExtensionKey(key2, localeEn);
        if (result2 == null) {
            throw new AssertionError("Expected non-null result for known key");
        }

        // Test 5: Locale not in langtags - should still not throw, but may return key
        Locale localeDe = Locale.GERMAN;
        String result3 = provider.getDisplayUnicodeExtensionKey("calendar", localeDe);
        if (result3 == null) {
            throw new AssertionError("Expected non-null result even for unsupported locale");
        }

        System.out.println("All tests passed.");
    }
}
