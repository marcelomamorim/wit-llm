/*
 * @test
 * @summary Test HijrahEra.getDisplayName(TextStyle, Locale) for normal and null argument cases
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.DateTimeException;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameNormal();
        testGetDisplayNameNullStyle();
        testGetDisplayNameNullLocale();
    }

    private static void testGetDisplayNameNormal() {
        HijrahEra era = HijrahEra.AH;
        // Use a valid TextStyle and Locale
        String name = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for valid inputs");
        }
    }

    private static void testGetDisplayNameNullStyle() {
        HijrahEra era = HijrahEra.AH;
        try {
            era.getDisplayName(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null TextStyle");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetDisplayNameNullLocale() {
        HijrahEra era = HijrahEra.AH;
        // Null locale is allowed and should default to system locale without exception
        String name = era.getDisplayName(TextStyle.FULL, null);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for null Locale");
        }
    }
}
