/*
 * @test
 * @summary Verifies TimeZone.getDisplayName(boolean, int) uses the default DISPLAY locale and returns the expected names
 * @run main TimeZoneWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {

    public static void main(String[] args) {
        Locale saved = Locale.getDefault(Locale.Category.DISPLAY);
        try {
            Locale.setDefault(Locale.Category.DISPLAY, Locale.US);
            TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");

            assertEquals("Pacific Standard Time", tz.getDisplayName(false, TimeZone.LONG));
            assertEquals("Pacific Daylight Time", tz.getDisplayName(true, TimeZone.LONG));
            assertEquals("PST", tz.getDisplayName(false, TimeZone.SHORT));
            assertEquals("PDT", tz.getDisplayName(true, TimeZone.SHORT));

            assertEquals(tz.getDisplayName(false, TimeZone.LONG, Locale.US),
                         tz.getDisplayName(false, TimeZone.LONG));
            assertEquals(tz.getDisplayName(true, TimeZone.SHORT, Locale.US),
                         tz.getDisplayName(true, TimeZone.SHORT));

            String std = tz.getDisplayName(false, TimeZone.LONG);
            String dst = tz.getDisplayName(true, TimeZone.LONG);
            if (std.equals(dst)) {
                throw new RuntimeException("Standard and daylight names must differ: " + std);
            }
        } finally {
            Locale.setDefault(Locale.Category.DISPLAY, saved);
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
