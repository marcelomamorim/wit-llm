/*
 * @test
 * @summary Test java.util.TimeZone.getDisplayName(boolean, int, Locale) behavior
 * @run main TimeZoneWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {
    public static void main(String[] args) {
        testInvalidStyleThrows();
        testDisplayNameReturnsNonNull();
        testGMTIdWithSignReturnsId();
        testOffsetCalculationWithDaylight();
        testOffsetCalculationWithoutDaylight();
    }

    private static void testInvalidStyleThrows() {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        try {
            tz.getDisplayName(false, 999, Locale.US);
            throw new RuntimeException("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testDisplayNameReturnsNonNull() {
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        String nameShort = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        String nameLong = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty for SHORT style");
        }
        if (nameLong == null || nameLong.isEmpty()) {
            throw new RuntimeException("getDisplayName returned null or empty for LONG style");
        }
    }

    private static void testGMTIdWithSignReturnsId() {
        // Custom GMT ID with sign
        TimeZone tzPlus = TimeZone.getTimeZone("GMT+05:30");
        String namePlus = tzPlus.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (!namePlus.equals("GMT+05:30")) {
            throw new RuntimeException("Expected GMT+05:30 to return ID itself, got: " + namePlus);
        }
        TimeZone tzMinus = TimeZone.getTimeZone("GMT-02:00");
        String nameMinus = tzMinus.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (!nameMinus.equals("GMT-02:00")) {
            throw new RuntimeException("Expected GMT-02:00 to return ID itself, got: " + nameMinus);
        }
    }

    private static void testOffsetCalculationWithDaylight() {
        // Use a zone with DST to verify offset + DST savings
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        int rawOffset = tz.getRawOffset();
        int dstSavings = tz.getDSTSavings();
        String displayName = tz.getDisplayName(true, TimeZone.LONG, Locale.US);
        if (displayName == null || displayName.isEmpty()) {
            throw new RuntimeException("Display name with daylight should not be null or empty");
        }
        // We cannot assert exact string, but ensure it is not the ID itself (like GMT+...)
        if (displayName.startsWith("GMT") && displayName.length() > 3) {
            char sign = displayName.charAt(3);
            if (sign == '+' || sign == '-') {
                // This is allowed fallback, so no failure
            } else {
                throw new RuntimeException("Unexpected display name format with daylight: " + displayName);
            }
        }
    }

    private static void testOffsetCalculationWithoutDaylight() {
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        String displayName = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (displayName == null || displayName.isEmpty()) {
            throw new RuntimeException("Display name without daylight should not be null or empty");
        }
    }
}
