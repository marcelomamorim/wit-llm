/*
 * @test
 * @summary WITUP-derived checks for LocaleNameProviderImpl.getDisplayScript null-argument handling
 * @modules java.base/sun.util.locale.provider
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        Set<String> tags = new HashSet<>(Arrays.asList("en", "fr"));
        LocaleNameProviderImpl provider =
                new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        expectNullPointerException(provider, null, Locale.US);
        expectNullPointerException(provider, "Latn", null);
        expectNullPointerException(provider, null, null);

        provider.getDisplayScript("Latn", Locale.US);
    }

    private static void expectNullPointerException(LocaleNameProviderImpl provider,
                                                   String scriptCode,
                                                   Locale locale) {
        try {
            provider.getDisplayScript(scriptCode, locale);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for scriptCode="
                + scriptCode + ", locale=" + locale);
    }
}
