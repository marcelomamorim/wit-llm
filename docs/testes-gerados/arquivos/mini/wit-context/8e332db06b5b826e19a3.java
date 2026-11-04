/*
 * @test
 * @summary Test sun.util.locale.provider.LocaleNameProviderImpl.getDisplayUnicodeExtensionType for null checks and normal behavior
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        testNullExtTypeThrowsNPE();
        testNullKeyThrowsNPE();
        testNullLocaleThrowsNPE();
        testNormalBehaviorReturnsExpected();
    }

    private static void testNullExtTypeThrowsNPE() {
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());
        try {
            provider.getDisplayUnicodeExtensionType(null, "key", Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null extType");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullKeyThrowsNPE() {
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());
        try {
            provider.getDisplayUnicodeExtensionType("extType", null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null key when extType is not null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullLocaleThrowsNPE() {
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());
        try {
            provider.getDisplayUnicodeExtensionType("extType", "key", null);
            throw new RuntimeException("Expected NullPointerException for null locale when extType and key are not null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNormalBehaviorReturnsExpected() {
        // Setup with a langtags set containing "en" to allow locale support
        java.util.Set<String> langtags = new HashSet<String>();
        langtags.add("en");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // The key constructed is "type." + key + "." + extType
        // Since getDisplayString returns the key itself if no localized name,
        // the method returns extType if the key equals the returned string.
        String extType = "extType";
        String key = "key";
        Locale locale = Locale.ENGLISH;

        String result = provider.getDisplayUnicodeExtensionType(extType, key, locale);

        // The expected result is either extType or a localized string different from the key
        // We assert that result is not null and is either extType or not equal to the constructed key
        if (result == null) {
            throw new RuntimeException("Returned null for valid inputs");
        }

        String expectedKey = "type." + key + "." + extType;
        if (!result.equals(extType) && result.equals(expectedKey)) {
            throw new RuntimeException("Returned key string instead of extType or localized name");
        }
    }
}
