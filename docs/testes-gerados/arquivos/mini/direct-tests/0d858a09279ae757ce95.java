/*
 * @test
 * @summary Test getDisplayName method of CurrencyNameProviderImpl
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CurrencyNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a dummy langtags set with a known locale
        Set<String> langtags = Set.of("en-US", "fr-FR");
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(type, langtags);

        // Test getDisplayName with lowercase currency code
        String displayName = provider.getDisplayName("usd", Locale.US);
        if (displayName == null) {
            throw new RuntimeException("Expected non-null displayName for 'usd' and Locale.US");
        }

        // Test getDisplayName with uppercase currency code
        displayName = provider.getDisplayName("USD", Locale.US);
        if (displayName == null) {
            throw new RuntimeException("Expected non-null displayName for 'USD' and Locale.US");
        }

        // Test getDisplayName with mixed case currency code
        displayName = provider.getDisplayName("uSd", Locale.US);
        if (displayName == null) {
            throw new RuntimeException("Expected non-null displayName for 'uSd' and Locale.US");
        }

        // Test getDisplayName with a locale not in langtags - should still return a value or null
        // but no exception expected because getDisplayName does not validate locale
        displayName = provider.getDisplayName("usd", Locale.CHINA);
        // No exception expected, no assertion on null or not

        // Test getDisplayName with null currencyCode - expect NullPointerException
        try {
            provider.getDisplayName(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null currencyCode");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test getDisplayName with null locale - expect NullPointerException
        try {
            provider.getDisplayName("usd", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
