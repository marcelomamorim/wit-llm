/*
 * @test
 * @summary Test sun.util.locale.provider.CollatorProviderImpl.getInstance(Locale) for null and valid locales
 * @run main CollatorProviderImplWitupTest
 */

import java.text.Collator;
import java.util.Locale;
import java.util.HashSet;
import sun.util.locale.provider.CollatorProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CollatorProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetInstanceNullLocale();
        testGetInstanceValidLocale();
    }

    private static void testGetInstanceNullLocale() {
        CollatorProviderImpl provider = new CollatorProviderImpl(LocaleProviderAdapter.Type.JRE, new HashSet<String>());
        try {
            provider.getInstance(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetInstanceValidLocale() throws Exception {
        // Use Locale.US which is commonly supported
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en-US");
        CollatorProviderImpl provider = new CollatorProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        Locale locale = Locale.US;
        Collator collator = provider.getInstance(locale);
        if (collator == null) {
            throw new RuntimeException("getInstance returned null for valid locale");
        }
        // The returned collator should be a clone, so modifying it should not affect the provider's internal state
        collator.setStrength(Collator.PRIMARY);
        Collator collator2 = provider.getInstance(locale);
        if (collator2 == null) {
            throw new RuntimeException("getInstance returned null on second call");
        }
        if (collator == collator2) {
            throw new RuntimeException("getInstance did not return a clone");
        }
    }
}
