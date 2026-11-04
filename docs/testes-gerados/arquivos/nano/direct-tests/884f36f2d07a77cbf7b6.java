/* @test    @summary Test getDisplayLanguage method of LocaleNameProviderImpl    @run main LocaleNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayLanguageWithValidInputs();
        testGetDisplayLanguageWithNullLanguage();
        testGetDisplayLanguageWithNullLocale();
        testGetDisplayLanguageWithInvalidLanguageFormat();
    }

    static void testGetDisplayLanguageWithValidInputs() {
        Locale locale = Locale.ENGLISH;
        Set<String> langtags = java.util.Collections.singleton("en");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, langtags);
        String result = provider.getDisplayLanguage("en", locale);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty string for valid inputs");
        }
    }

    static void testGetDisplayLanguageWithNullLanguage() {
        try {
            Locale locale = Locale.ENGLISH;
            Set<String> langtags = java.util.Collections.singleton("en");
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, langtags);
            provider.getDisplayLanguage(null, locale);
            throw new AssertionError("Expected NullPointerException for null language");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetDisplayLanguageWithNullLocale() {
        try {
            Locale locale = null;
            Set<String> langtags = java.util.Collections.singleton("en");
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, langtags);
            provider.getDisplayLanguage("en", locale);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetDisplayLanguageWithInvalidLanguageFormat() {
        Locale locale = Locale.ENGLISH;
        Set<String> langtags = java.util.Collections.singleton("en");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, langtags);
        String result = provider.getDisplayLanguage("eng", locale);
        if (result != null) {
            throw new AssertionError("Expected null for invalid language format");
        }
    }
}
