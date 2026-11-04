/*
 * @test
 * @summary Verifies that getDisplayUnicodeExtensionType throws NullPointerException for null extType, key, or locale arguments
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Collections;
import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        LocaleNameProviderImpl provider =
            new LocaleNameProviderImpl(null, Collections.<String>emptySet());

        expectNpe(provider, null, "key", Locale.US, "extType == null");
        expectNpe(provider, null, null, Locale.US, "extType == null and key == null");
        expectNpe(provider, "calendar", null, Locale.US, "key == null with extType != null");
        expectNpe(provider, "calendar", "ca", null, "locale == null with extType and key non-null");
    }

    private static void expectNpe(LocaleNameProviderImpl provider, String extType,
                                  String key, Locale locale, String label) {
        try {
            provider.getDisplayUnicodeExtensionType(extType, key, locale);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for: " + label);
    }
}
