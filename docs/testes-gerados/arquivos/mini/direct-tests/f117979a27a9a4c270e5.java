/*
 * @test
 * @summary Test getTimeInstance method of DateFormatProviderImpl
 * @run main DateFormatProviderImplWitupTest
 */

import java.text.DateFormat;
import java.util.HashSet;
import java.util.Locale;
import sun.util.locale.provider.DateFormatProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class DateFormatProviderImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup a minimal langtags set with "en"
        HashSet<String> langtags = new HashSet<>();
        langtags.add("en");

        // Use LocaleProviderAdapter.Type.JRE which is a valid enum constant
        DateFormatProviderImpl provider = new DateFormatProviderImpl(LocaleProviderAdapter.Type.JRE, langtags);

        // Test with valid style constants and supported locale
        Locale locale = Locale.ENGLISH;

        // Valid styles: DateFormat.SHORT, MEDIUM, LONG, FULL
        int[] validStyles = {DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL};
        for (int style : validStyles) {
            DateFormat df = provider.getTimeInstance(style, locale);
            if (df == null) {
                throw new RuntimeException("getTimeInstance returned null for style " + style);
            }
            // Check that the locale of the DateFormat matches the requested locale
            if (!locale.equals(df.getLocale())) {
                throw new RuntimeException("DateFormat locale mismatch for style " + style);
            }
        }

        // Test with invalid style (e.g. -2) - expect IllegalArgumentException
        try {
            provider.getTimeInstance(-2, locale);
            throw new RuntimeException("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test with null locale - expect NullPointerException
        try {
            provider.getTimeInstance(DateFormat.SHORT, null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test with unsupported locale - expect IllegalArgumentException
        Locale unsupportedLocale = new Locale("zz", "ZZ");
        try {
            provider.getTimeInstance(DateFormat.SHORT, unsupportedLocale);
            throw new RuntimeException("Expected IllegalArgumentException for unsupported locale");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
