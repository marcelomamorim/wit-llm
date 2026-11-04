/*
 * @test
 * @summary Basic tests for DateFormatProviderImpl.getDateInstance(int, Locale)
 * @run main DateFormatProviderImplWitupTest
 */

import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;
import java.text.DateFormat;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup a minimal langtags set with "en"
        Set<String> langtags = new HashSet<>();
        langtags.add("en");

        // Use a known LocaleProviderAdapter.Type
        LocaleProviderAdapter.Type type = LocaleProviderAdapter.Type.JRE;

        DateFormatProviderImpl provider = new DateFormatProviderImpl(type, langtags);

        // Test with valid style and supported locale
        Locale locale = Locale.ENGLISH;
        DateFormat df = provider.getDateInstance(DateFormat.SHORT, locale);
        if (df == null) {
            throw new RuntimeException("getDateInstance returned null for valid style and locale");
        }

        // Test with all valid styles
        int[] validStyles = {DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL};
        for (int style : validStyles) {
            DateFormat dfi = provider.getDateInstance(style, locale);
            if (dfi == null) {
                throw new RuntimeException("getDateInstance returned null for style " + style);
            }
        }

        // Test with unsupported locale (not in langtags)
        Locale unsupportedLocale = Locale.FRENCH;
        try {
            provider.getDateInstance(DateFormat.SHORT, unsupportedLocale);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test with null locale - expect NullPointerException
        try {
            provider.getDateInstance(DateFormat.SHORT, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected
        }

        // Test with invalid style - expect IllegalArgumentException
        int invalidStyle = -99;
        try {
            provider.getDateInstance(invalidStyle, locale);
            throw new RuntimeException("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
