/*
 * @test
 * @summary Verifies LocaleNameProviderImpl.getDisplayScript returns localized script names and null for unknown codes
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
        tags.add("en-US");
        LocaleNameProviderImpl provider =
                new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, tags);

        String latin = provider.getDisplayScript("Latn", Locale.US);
        if (!"Latin".equals(latin)) {
            throw new RuntimeException("Expected 'Latin' but got: " + latin);
        }

        String unknown = provider.getDisplayScript("Zzzz", Locale.US);
        if (unknown != null) {
            throw new RuntimeException("Expected null for unknown script but got: " + unknown);
        }
    }
}
