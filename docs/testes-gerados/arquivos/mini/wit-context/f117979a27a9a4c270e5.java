/*
 * @test
 * @summary Test sun.util.locale.provider.DateFormatProviderImpl.getTimeInstance(int, Locale) for null locale and valid inputs
 * @run main DateFormatProviderImplWitupTest
 */

import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Locale;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) {
        testGetTimeInstanceWithNullLocale();
        testGetTimeInstanceWithValidLocale();
    }

    private static void testGetTimeInstanceWithNullLocale() {
        // Setup with dummy type and empty langtags
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        DateFormatProviderImpl provider = new DateFormatProviderImpl(dummyType, new HashSet<String>());

        try {
            provider.getTimeInstance(DateFormat.SHORT, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetTimeInstanceWithValidLocale() {
        // Setup with JRE type and langtags containing "en-US"
        HashSet<String> langtags = new HashSet<String>();
        langtags.add("en-US");
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;
        DateFormatProviderImpl provider = new DateFormatProviderImpl(type, langtags);

        Locale locale = Locale.forLanguageTag("en-US");

        // The locale must be supported to avoid IllegalArgumentException
        if (!provider.isSupportedLocale(locale)) {
            throw new RuntimeException("Locale en-US not supported by provider");
        }

        DateFormat df = provider.getTimeInstance(DateFormat.MEDIUM, locale);
        if (df == null) {
            throw new RuntimeException("getTimeInstance returned null for valid locale");
        }

        // Check that the returned DateFormat formats a time string (basic check)
        String formatted = df.format(new java.util.Date());
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatted time string is null or empty");
        }
    }
}
