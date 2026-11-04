/*
 * @test
 * @summary Test getDisplayLanguage method of LocaleNameProviderImpl
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
sun.util.locale.provider.LocaleNameProviderImpl;
sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a LocaleProviderAdapter.Type stub for testing
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = Set.of("en", "fr", "de");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(type, langtags);

        // Test getDisplayLanguage with valid language and supported locale
        String displayEnInFr = provider.getDisplayLanguage("en", Locale.FRANCE);
        if (displayEnInFr == null) {
            throw new RuntimeException("Expected non-null display language for 'en' in fr locale");
        }

        // Test getDisplayLanguage with valid language and unsupported locale
        Locale unsupportedLocale = new Locale("zz", "ZZ");
        String displayEnInUnsupported = provider.getDisplayLanguage("en", unsupportedLocale);
        if (displayEnInUnsupported != null) {
            throw new RuntimeException("Expected null display language for unsupported locale");
        }

        // Test getDisplayLanguage with language not in langtags
        String displayEsInEn = provider.getDisplayLanguage("es", Locale.ENGLISH);
        if (displayEsInEn != null) {
            throw new RuntimeException("Expected null display language for language not in langtags");
        }

        // Test getDisplayLanguage with null language argument - expect NullPointerException
        try {
            provider.getDisplayLanguage(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null language");
        } catch (NullPointerException e) {
            // expected
        }

        // Test getDisplayLanguage with null locale argument - expect NullPointerException
        try {
            provider.getDisplayLanguage("en", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }

        // Test getDisplayLanguage with invalid language code format - expect IllegalArgumentException
        try {
            provider.getDisplayLanguage("EN", Locale.ENGLISH);
            throw new RuntimeException("Expected IllegalArgumentException for invalid language code format");
        } catch (IllegalArgumentException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
