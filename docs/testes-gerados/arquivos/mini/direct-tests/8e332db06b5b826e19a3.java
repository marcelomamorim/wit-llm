/*
 * @test
 * @summary Test getDisplayUnicodeExtensionType method of LocaleNameProviderImpl
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a LocaleNameProviderImpl with a dummy type and langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");
        langtags.add("fr");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, langtags);

        // Test 1: null check enforced by super call - expect NullPointerException if any argument is null
        try {
            provider.getDisplayUnicodeExtensionType(null, "key", Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null extType");
        } catch (NullPointerException expected) {
        }
        try {
            provider.getDisplayUnicodeExtensionType("extType", null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }
        try {
            provider.getDisplayUnicodeExtensionType("extType", "key", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }

        // Test 2: When getDisplayString returns rbKey, method returns extType
        // Use a key and extType that likely do not exist in resource bundles
        String extType = "nonexistentExtType";
        String key = "nonexistentKey";
        Locale locale = Locale.ENGLISH;
        String result = provider.getDisplayUnicodeExtensionType(extType, key, locale);
        String expectedRbKey = "type." + key + "." + extType;
        if (!result.equals(extType)) {
            throw new RuntimeException("Expected return extType when getDisplayString returns rbKey, got: " + result);
        }

        // Test 3: When getDisplayString returns a different string, method returns that string
        // We cannot easily mock getDisplayString, but we can test with known keys
        // Use a known language code and locale to get a localized name
        // For example, key="ca" extType="language" and locale=Locale.FRENCH might produce a localized string
        // But since getDisplayString is private and depends on langtags, we test with "en" and "fr"
        // We try to get display language for "fr" in English locale
        String knownExtType = "language";
        String knownKey = "fr";
        String displayName = provider.getDisplayUnicodeExtensionType(knownExtType, knownKey, Locale.ENGLISH);
        if (displayName.equals("type." + knownKey + "." + knownExtType)) {
            // If it returns rbKey, fallback to extType
            if (!displayName.equals(knownExtType)) {
                throw new RuntimeException("Unexpected return value for known key");
            }
        } else {
            // Otherwise, it returns a localized string different from rbKey
            if (displayName == null || displayName.isEmpty()) {
                throw new RuntimeException("Returned display name is null or empty");
            }
        }
    }
}
