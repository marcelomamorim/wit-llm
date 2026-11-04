/*
 * @test
 * @summary Test sun.util.locale.provider.DateFormatProviderImpl.getDateInstance(int, Locale) for null locale and valid inputs
 * @run main DateFormatProviderImplWitupTest
 */

import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) {
        testGetDateInstanceWithNullLocale();
        testGetDateInstanceWithValidLocale();
    }

    private static void testGetDateInstanceWithNullLocale() {
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        DateFormatProviderImpl provider = new DateFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        try {
            provider.getDateInstance(DateFormat.SHORT, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetDateInstanceWithValidLocale() {
        Set<String> langtags = new HashSet<>();
        langtags.add("en-US");
        DateFormatProviderImpl provider = new DateFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);
        Locale locale = Locale.forLanguageTag("en-US");
        DateFormat df = provider.getDateInstance(DateFormat.SHORT, locale);
        if (df == null) {
            throw new RuntimeException("getDateInstance returned null for valid locale");
        }
        // Additional sanity check: format current date
        String formatted = df.format(new java.util.Date());
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("DateFormat.format returned null or empty string");
        }
    }
}
