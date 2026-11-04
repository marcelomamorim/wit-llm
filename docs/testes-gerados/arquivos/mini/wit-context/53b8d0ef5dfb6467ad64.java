/*
 * @test
 * @summary Test java.time.temporal.IsoFields.getDisplayName(Locale) for null locale and valid locale
 * @run main IsoFieldsWitupTest
 */

import java.time.temporal.IsoFields;
import java.util.Locale;
import java.util.Objects;
import java.time.DateTimeException;

public class IsoFieldsWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameWithNullLocale();
        testGetDisplayNameWithValidLocale();
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            IsoFields.INSTANCE.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayNameWithValidLocale() {
        Locale locale = Locale.ENGLISH;
        String name = IsoFields.INSTANCE.getDisplayName(locale);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for valid locale");
        }
    }
}
