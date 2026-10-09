/* @test    @summary Test getDisplayName for TimeZone class    @run main TimeZoneWitupTest_P5025747a32f4 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_P5025747a32f4 {

    public static void main(String[] args) {
        testGetDisplayNameValidStyles();
        testGetDisplayNameInvalidStyle();
        testGetDisplayNameGMTId();
        testGetDisplayNameWithNullLocale();
    }

    static void testGetDisplayNameValidStyles() {
        try {
            TimeZone tz = TimeZone.getDefault();
            String nameShort = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
            String nameLong = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
            if (nameShort == null || nameLong == null) {
                throw new AssertionError("Display name should not be null for valid styles");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetDisplayNameValidStyles: " + e);
        }
    }

    static void testGetDisplayNameInvalidStyle() {
        try {
            TimeZone tz = TimeZone.getDefault();
            int invalidStyle = 42; // invalid style
            tz.getDisplayName(true, invalidStyle, Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testGetDisplayNameGMTId() {
        try {
            TimeZone tz = TimeZone.getTimeZone("GMT+05:30");
            String name = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
            if (name == null || !name.contains("GMT")) {
                throw new AssertionError("Display name for GMT ID should contain 'GMT'");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetDisplayNameGMTId: " + e);
        }
    }

    static void testGetDisplayNameWithNullLocale() {
        try {
            TimeZone tz = TimeZone.getDefault();
            String name = tz.getDisplayName(true, TimeZone.SHORT, null);
            if (name == null) {
                throw new AssertionError("Display name with null locale should not be null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetDisplayNameWithNullLocale: " + e);
        }
    }
}
