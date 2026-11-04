/*
 * @test
 * @summary Test java.util.TimeZone.getDisplayName(boolean, int) basic behavior
 * @run main TimeZoneWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {
    public static void main(String[] args) {
        // Use a known time zone
        TimeZone tz = TimeZone.getTimeZone("GMT");

        // Test getDisplayName with daylight = false, style = TimeZone.LONG (0)
        String name1 = tz.getDisplayName(false, TimeZone.LONG);
        if (name1 == null || name1.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty string for LONG standard time");
        }

        // Test getDisplayName with daylight = true, style = TimeZone.SHORT (1)
        String name2 = tz.getDisplayName(true, TimeZone.SHORT);
        if (name2 == null || name2.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty string for SHORT daylight time");
        }

        // Test getDisplayName with daylight = false, style = invalid style (e.g. 999)
        // According to JDK 11 behavior, invalid style falls back to LONG
        String name3 = tz.getDisplayName(false, 999);
        if (name3 == null || name3.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty string for invalid style");
        }

        // Test getDisplayName with daylight = true, style = TimeZone.LONG (0)
        String name4 = tz.getDisplayName(true, TimeZone.LONG);
        if (name4 == null || name4.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty string for LONG daylight time");
        }

        // Test that getDisplayName returns a string different for daylight true vs false if applicable
        // For GMT, daylight savings is not used, so names may be equal
        // So just check that method returns non-null strings

        // Test getDisplayName with default Locale
        Locale defaultLocale = Locale.getDefault(Locale.Category.DISPLAY);
        if (defaultLocale == null) {
            throw new RuntimeException("Default Locale for DISPLAY category is null");
        }

        System.out.println("All getDisplayName tests passed.");
    }
}
