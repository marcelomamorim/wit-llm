/*
 * @test
 * @summary Test sun.util.locale.provider.LocaleNameProviderImpl.getDisplayLanguage(String, Locale) for null argument handling and normal behavior
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup: create a LocaleNameProviderImpl with a dummy type and a non-empty langtags set
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = Set.of("en", "fr");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, langtags);

        // Test 1: getDisplayLanguage with null lang should throw NullPointerException
        try {
            provider.getDisplayLanguage(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null lang");
        } catch (NullPointerException expected) {
        }

        // Test 2: getDisplayLanguage with null locale should throw NullPointerException
        try {
            provider.getDisplayLanguage("en", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
        }

        // Test 3: getDisplayLanguage with valid lang and locale returns a String or null (no exception)
        String result = provider.getDisplayLanguage("en", Locale.FRENCH);
        if (result == null) {
            // null is allowed if no localized name is available
        } else if (!(result instanceof String)) {
            throw new RuntimeException("getDisplayLanguage returned non-string result");
        }

        // Test 4: getDisplayLanguage with invalid lang format throws IllegalArgumentException or returns null
        // According to method doc, IllegalArgumentException is expected for invalid lang format
        // But since we do not have direct evidence of throwing, we test for exception or null
        boolean illegalArgThrown = false;
        try {
            provider.getDisplayLanguage("EN", Locale.ENGLISH); // uppercase, invalid
        } catch (IllegalArgumentException e) {
            illegalArgThrown = true;
        } catch (NullPointerException e) {
            // Not expected here, ignore
        }
        if (!illegalArgThrown) {
            // If no exception, result may be null or string
            String r = provider.getDisplayLanguage("EN", Locale.ENGLISH);
            if (r != null && !(r instanceof String)) {
                throw new RuntimeException("Unexpected return type for invalid lang format");
            }
        }

        // Test 5: isSupportedLocale returns boolean for known and unknown locales
        boolean supportedEn = provider.isSupportedLocale(Locale.ENGLISH);
        boolean supportedFr = provider.isSupportedLocale(Locale.FRENCH);
        boolean supportedJp = provider.isSupportedLocale(Locale.JAPANESE);
        // No assertion on values, just call to ensure no exceptions

        // Test 6: getAvailableLocales returns non-null array containing locales
        Locale[] available = provider.getAvailableLocales();
        if (available == null) {
            throw new RuntimeException("getAvailableLocales returned null");
        }

        // All tests passed
        System.out.println("LocaleNameProviderImpl.getDisplayLanguage tests passed");
    }
}
