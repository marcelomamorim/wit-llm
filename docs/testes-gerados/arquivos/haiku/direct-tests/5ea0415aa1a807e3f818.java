/*
 * @test
 * @summary Verify TimeZone.getDisplayName style validation and custom GMT ID handling
 * @run main TimeZoneWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {

    public static void main(String[] args) {
        testInvalidStyleRejected();
        testCustomGmtIdReturnsId();
        testNamedZoneReturnsNonEmptyNames();
        System.out.println("PASSED");
    }

    private static void testInvalidStyleRejected() {
        TimeZone tz = TimeZone.getTimeZone("GMT+10:00");
        int[] badStyles = {-1, 2, 100};
        for (int style : badStyles) {
            boolean thrown = false;
            try {
                tz.getDisplayName(false, style, Locale.US);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for style " + style);
            }
        }
    }

    private static void testCustomGmtIdReturnsId() {
        TimeZone tz = TimeZone.getTimeZone("GMT+10:00");
        String shortName = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        String longName = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        if (!"GMT+10:00".equals(shortName)) {
            throw new RuntimeException("Unexpected short name: " + shortName);
        }
        if (!"GMT+10:00".equals(longName)) {
            throw new RuntimeException("Unexpected long name: " + longName);
        }
    }

    private static void testNamedZoneReturnsNonEmptyNames() {
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        String shortName = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        String longName = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        String dstLongName = tz.getDisplayName(true, TimeZone.LONG, Locale.US);
        if (shortName == null || shortName.isEmpty()) {
            throw new RuntimeException("Short display name is null or empty");
        }
        if (longName == null || longName.isEmpty()) {
            throw new RuntimeException("Long display name is null or empty");
        }
        if (dstLongName == null || dstLongName.isEmpty()) {
            throw new RuntimeException("Daylight long display name is null or empty");
        }
    }
}
