/*
 * @test
 * @summary Test getDisplayScript method of LocaleNameProviderImpl
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup a dummy type and langtags for testing
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = Set.of("en", "fr", "ja");

        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, langtags);

        // Test with valid script code and supported locale
        Locale localeEn = Locale.ENGLISH;
        String scriptCode = "Latn";
        String result = provider.getDisplayScript(scriptCode, localeEn);
        // Result may be null or non-null depending on provider implementation, so just call and print
        if (result == null) {
            System.out.println("getDisplayScript returned null for scriptCode '" + scriptCode + "' and locale EN");
        } else {
            System.out.println("getDisplayScript returned: " + result);
        }

        // Test with null scriptCode - expect NullPointerException
        try {
            provider.getDisplayScript(null, localeEn);
            throw new RuntimeException("Expected NullPointerException for null scriptCode");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with null locale - expect NullPointerException
        try {
            provider.getDisplayScript(scriptCode, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with unsupported locale - expect IllegalArgumentException
        Locale unsupportedLocale = new Locale("zz", "ZZ");
        try {
            provider.getDisplayScript(scriptCode, unsupportedLocale);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        // Test with invalid scriptCode format - expect IllegalArgumentException
        String invalidScriptCode = "123";
        try {
            provider.getDisplayScript(invalidScriptCode, localeEn);
            throw new RuntimeException("Expected IllegalArgumentException for invalid scriptCode");
        } catch (IllegalArgumentException iae) {
            // expected
        }

        System.out.println("LocaleNameProviderImplWitupTest passed all tests.");
    }
}
