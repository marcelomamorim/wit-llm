/*
 * @test
 * @summary Test java.util.TimeZone.getDisplayName(boolean, int, Locale) for valid and invalid style values
 * @run main TimeZoneWitupTest_Paacf01ef9d6f
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_Paacf01ef9d6f {
    public static void main(String[] args) {
        testValidStyles();
        testInvalidStyleThrows();
    }

    private static void testValidStyles() {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Locale locale = Locale.US;

        // style SHORT (0) and LONG (1) are valid
        String shortName = tz.getDisplayName(false, TimeZone.SHORT, locale);
        if (shortName == null || shortName.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty for SHORT style");
        }

        String longName = tz.getDisplayName(true, TimeZone.LONG, locale);
        if (longName == null || longName.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty for LONG style");
        }
    }

    private static void testInvalidStyleThrows() {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Locale locale = Locale.US;

        int[] invalidStyles = { -1, 2, 100, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int style : invalidStyles) {
            boolean thrown = false;
            try {
                tz.getDisplayName(false, style, locale);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for style: " + style);
            }
        }
    }
}
