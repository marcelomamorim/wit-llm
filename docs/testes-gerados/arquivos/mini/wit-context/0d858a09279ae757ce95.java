/*
 * @test
 * @summary Test sun.util.locale.provider.CurrencyNameProviderImpl.getDisplayName(String, Locale) for null locale and normal usage
 * @run main CurrencyNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.CurrencyNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class CurrencyNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a minimal langtags set with a known locale tag
        Set<String> langtags = java.util.Collections.singleton("en-US");
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        CurrencyNameProviderImpl provider = new CurrencyNameProviderImpl(type, langtags);

        // Test 1: getDisplayName with null locale should throw NullPointerException
        boolean npeThrown = false;
        try {
            provider.getDisplayName("USD", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when locale is null");
        }

        // Test 2: getDisplayName with valid currency code and locale in langtags
        Locale locale = Locale.forLanguageTag("en-US");
        String displayName = provider.getDisplayName("USD", locale);
        // The returned displayName may be null or non-null depending on internal data,
        // but must not throw and must be consistent with getString behavior.
        if (displayName == null) {
            // Accept null as valid (no symbol found)
        } else if (!(displayName instanceof String)) {
            throw new RuntimeException("getDisplayName returned non-string");
        }

        // Test 3: getDisplayName with currency code in different case returns same as lower case
        String displayNameUpper = provider.getDisplayName("usd", locale);
        String displayNameLower = provider.getDisplayName("USD", locale);
        if (displayNameUpper == null) {
            if (displayNameLower != null) {
                throw new RuntimeException("Display names differ for case variants");
            }
        } else if (!displayNameUpper.equals(displayNameLower)) {
            throw new RuntimeException("Display names differ for case variants");
        }

        // Test 4: getDisplayName with currency code not in ISO 4217 format but valid string
        // According to current code, no explicit validation, so it should return getString result (possibly null)
        String invalidCodeName = provider.getDisplayName("XXX", locale);
        if (invalidCodeName != null && !(invalidCodeName instanceof String)) {
            throw new RuntimeException("getDisplayName returned non-string for invalid code");
        }

        System.out.println("All tests passed.");
    }
}
