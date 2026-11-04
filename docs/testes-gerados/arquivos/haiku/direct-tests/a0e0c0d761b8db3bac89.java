/*
 * @test
 * @summary Verifies public behavior of LocaleNameProviderImpl.getDisplayCountry
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
        Set<String> langtags = new HashSet<>();
        langtags.add("en");
        langtags.add("en-US");
        LocaleNameProviderImpl provider =
                new LocaleNameProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        String us = provider.getDisplayCountry("US", Locale.US);
        if (us == null || !us.equals("United States")) {
            throw new RuntimeException("Expected 'United States' but got: " + us);
        }

        String again = provider.getDisplayCountry("US", Locale.US);
        if (!us.equals(again)) {
            throw new RuntimeException("Non-deterministic result: " + us + " vs " + again);
        }

        String unknown = provider.getDisplayCountry("ZZ", Locale.US);
        if (unknown != null) {
            throw new RuntimeException("Expected null for unknown country but got: " + unknown);
        }
    }
}
