/*
 * @test
 * @summary Test java.time.chrono.HijrahEra.getDisplayName(TextStyle, Locale) method
 * @run main HijrahEraWitupTest
 */

import java.time.chrono.HijrahEra;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class HijrahEraWitupTest {
    public static void main(String[] args) {
        // Test getDisplayName with FULL style and Locale.ENGLISH
        String nameFullEnglish = HijrahEra.AH.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameFullEnglish == null || nameFullEnglish.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for FULL style and Locale.ENGLISH");
        }

        // Test getDisplayName with SHORT style and Locale.ENGLISH
        String nameShortEnglish = HijrahEra.AH.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        if (nameShortEnglish == null || nameShortEnglish.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for SHORT style and Locale.ENGLISH");
        }

        // Test getDisplayName with NARROW style and Locale.ENGLISH
        String nameNarrowEnglish = HijrahEra.AH.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        if (nameNarrowEnglish == null || nameNarrowEnglish.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for NARROW style and Locale.ENGLISH");
        }

        // Test getDisplayName with FULL style and Locale.FRENCH
        String nameFullFrench = HijrahEra.AH.getDisplayName(TextStyle.FULL, Locale.FRENCH);
        if (nameFullFrench == null || nameFullFrench.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for FULL style and Locale.FRENCH");
        }

        // Test getDisplayName with SHORT style and Locale.FRENCH
        String nameShortFrench = HijrahEra.AH.getDisplayName(TextStyle.SHORT, Locale.FRENCH);
        if (nameShortFrench == null || nameShortFrench.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for SHORT style and Locale.FRENCH");
        }

        // Test getDisplayName with NARROW style and Locale.FRENCH
        String nameNarrowFrench = HijrahEra.AH.getDisplayName(TextStyle.NARROW, Locale.FRENCH);
        if (nameNarrowFrench == null || nameNarrowFrench.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for NARROW style and Locale.FRENCH");
        }

        // Test getDisplayName with null Locale (should default to system locale)
        String nameNullLocale = HijrahEra.AH.getDisplayName(TextStyle.FULL, null);
        if (nameNullLocale == null || nameNullLocale.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for FULL style and null Locale");
        }

        // Test getDisplayName with null TextStyle (expect NullPointerException)
        boolean npeThrown = false;
        try {
            HijrahEra.AH.getDisplayName(null, Locale.ENGLISH);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when TextStyle is null");
        }

        System.out.println("All tests passed.");
    }
}
