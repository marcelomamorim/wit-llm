/*
 * @test
 * @summary Verifies getDisplayUnicodeExtensionType null checks and fallback to extType for unknown keys
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = new HashSet<>();
        tags.add("en");
        LocaleNameProviderImpl provider =
                new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        testUnknownKeyFallsBackToExtType(provider);
        testNullExtTypeThrowsNPE(provider);
        testNullKeyThrowsNPE(provider);
        testNullLocaleThrowsNPE(provider);
    }

    private static void testUnknownKeyFallsBackToExtType(LocaleNameProviderImpl provider) {
        String extType = "zzzzqq";
        String result = provider.getDisplayUnicodeExtensionType(extType, "ca", Locale.US);
        if (!extType.equals(result)) {
            throw new RuntimeException("Expected fallback to extType but got: " + result);
        }
    }

    private static void testNullExtTypeThrowsNPE(LocaleNameProviderImpl provider) {
        try {
            provider.getDisplayUnicodeExtensionType(null, "ca", Locale.US);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null extType");
    }

    private static void testNullKeyThrowsNPE(LocaleNameProviderImpl provider) {
        try {
            provider.getDisplayUnicodeExtensionType("zzzzqq", null, Locale.US);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null key");
    }

    private static void testNullLocaleThrowsNPE(LocaleNameProviderImpl provider) {
        try {
            provider.getDisplayUnicodeExtensionType("zzzzqq", "ca", null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null locale");
    }
}
