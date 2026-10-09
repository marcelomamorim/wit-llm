/*
 * @test
 * @summary Test for TimeZone.getDisplayName method behavior.
 * @run main TimeZoneWitupTest_P3719cbf11f32
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_P3719cbf11f32 {

    public static void main(String[] args) {
        testGetDisplayNameWithValidStyles();
        testGetDisplayNameWithInvalidStyle();
        testGetDisplayNameWithGMTId();
        testGetDisplayNameWithUnknownId();
    }

    static void testGetDisplayNameWithValidStyles() {
        TimeZone tz = TimeZone.getDefault();
        String nameShort = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
        String nameLong = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (nameShort == null || nameLong == null) {
            throw new AssertionError("Display names should not be null for valid styles");
        }
    }

    static void testGetDisplayNameWithInvalidStyle() {
        TimeZone tz = TimeZone.getDefault();
        try {
            tz.getDisplayName(true, 2, Locale.US); // invalid style
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testGetDisplayNameWithGMTId() {
        TimeZone gmtTz = TimeZone.getTimeZone("GMT+02:00");
        String name = gmtTz.getDisplayName(true, TimeZone.SHORT, Locale.US);
        if (name == null || !name.contains("GMT")) {
            throw new AssertionError("Display name for GMT ID should contain 'GMT'");
        }
    }

    static void testGetDisplayNameWithUnknownId() {
        TimeZone unknownTz = TimeZone.getTimeZone("Unknown/Zone");
        String name = unknownTz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (name == null) {
            throw new AssertionError("Display name for unknown ID should not be null");
        }
    }
}
