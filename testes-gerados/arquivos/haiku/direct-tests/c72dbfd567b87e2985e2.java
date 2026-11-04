/*
 * @test
 * @summary Null argument checks for LocaleNameProviderImpl.getDisplayUnicodeExtensionKey
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
        expectNpe(provider, null, Locale.US, "null key");
        expectNpe(provider, "ca", null, "null locale");
        expectNpe(provider, null, null, "both null");
    }

    private static void expectNpe(LocaleNameProviderImpl provider, String key,
                                  Locale locale, String description) {
        boolean thrown = false;
        try {
            provider.getDisplayUnicodeExtensionKey(key, locale);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for " + description);
        }
    }
}
