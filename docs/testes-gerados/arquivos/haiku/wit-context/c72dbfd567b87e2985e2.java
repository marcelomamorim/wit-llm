/*
 * @test
 * @summary Verifies that LocaleNameProviderImpl.getDisplayUnicodeExtensionKey rejects null key and null locale with NullPointerException
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(null, new HashSet<String>());
        expectNPE(provider, null, Locale.US, "null key");
        expectNPE(provider, "ca", null, "null locale");
        expectNPE(provider, null, null, "null key and null locale");
        System.out.println("PASSED");
    }

    static void expectNPE(LocaleNameProviderImpl provider, String key, Locale locale, String what) {
        try {
            provider.getDisplayUnicodeExtensionKey(key, locale);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for " + what);
    }
}
