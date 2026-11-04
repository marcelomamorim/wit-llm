/*
 * @test
 * @summary Verifies that LocaleNameProviderImpl.getDisplayLanguage rejects null arguments with NullPointerException and returns no empty name for a valid request
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(
                LocaleProviderAdapter.Type.JRE,
                new HashSet<>(Arrays.asList("en", "fr")));

        checkNullPointer(provider, null, Locale.US);
        checkNullPointer(provider, "en", null);
        checkNullPointer(provider, null, null);

        String name = provider.getDisplayLanguage("en", Locale.US);
        if (name != null && name.isEmpty()) {
            throw new RuntimeException("getDisplayLanguage returned an empty name");
        }
    }

    private static void checkNullPointer(LocaleNameProviderImpl provider, String lang, Locale locale) {
        try {
            provider.getDisplayLanguage(lang, locale);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for lang=" + lang + ", locale=" + locale);
    }
}
