/*
 * @test
 * @summary Test java.time.temporal.IsoFields.getDisplayName(Locale) method
 * @run main IsoFieldsWitupTest
 */

import java.time.temporal.IsoFields;
import java.util.Locale;
import java.util.Objects;

public class IsoFieldsWitupTest {
    public static void main(String[] args) throws Exception {
        testGetDisplayNameWithValidLocale();
        testGetDisplayNameWithNullLocale();
    }

    private static void testGetDisplayNameWithValidLocale() {
        Locale locale = Locale.ENGLISH;
        String name = IsoFields.WEEK_BASED_YEAR.getDisplayName(locale);
        // The returned name should be non-null and non-empty
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for Locale.ENGLISH");
        }
        // The returned name should be either the resource bundle string or toString()
        String toStringName = IsoFields.WEEK_BASED_YEAR.toString();
        if (!name.equals(toStringName) && !name.equals("week")) {
            // "week" is the key checked in the resource bundle, so allow it
            // If resource bundle does not contain "field.week", toString() is returned
            // So name must be either "week" or toString()
            throw new AssertionError("getDisplayName returned unexpected string: " + name);
        }
    }

    private static void testGetDisplayNameWithNullLocale() {
        try {
            IsoFields.WEEK_BASED_YEAR.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
