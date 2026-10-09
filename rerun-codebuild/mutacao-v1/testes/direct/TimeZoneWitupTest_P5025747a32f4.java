/* @test    @summary Test TimeZone.getDisplayName with various parameters    @run main TimeZoneWitupTest_P5025747a32f4 */

import java.util.TimeZone;
import java.util.Locale;

public class TimeZoneWitupTest_P5025747a32f4 {

    public static void main(String[] args) {
        testGetDisplayNameStandard();
        testGetDisplayNameInvalidStyle();
        testGetDisplayNameGMTId();
        testGetDisplayNameWithDSTSavings();
    }

    static void testGetDisplayNameStandard() {
        TimeZone tz = TimeZone.getDefault();
        String nameShort = tz.getDisplayName(false, 0, Locale.US);
        String nameLong = tz.getDisplayName(false, 1, Locale.US);
        if (nameShort == null || nameLong == null) {
            throw new AssertionError("Display name should not be null for standard style");
        }
        System.out.println("PASS: testGetDisplayNameStandard");
    }

    static void testGetDisplayNameInvalidStyle() {
        try {
            TimeZone tz = TimeZone.getDefault();
            tz.getDisplayName(true, 3, Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: testGetDisplayNameInvalidStyle");
        }
    }

    static void testGetDisplayNameGMTId() {
        TimeZone tz = TimeZone.getTimeZone("GMT+02:00");
        String name = tz.getDisplayName(false, 0, Locale.US);
        if (!name.startsWith("GMT")) {
            throw new AssertionError("Display name for GMT ID should start with 'GMT'");
        }
        System.out.println("PASS: testGetDisplayNameGMTId");
    }

    static void testGetDisplayNameWithDSTSavings() {
        TimeZone tz = TimeZone.getDefault();
        int rawOffset = tz.getRawOffset();
        int dstSavings = tz.getDSTSavings();
        String name = tz.getDisplayName(true, 1, Locale.US);
        if (name == null) {
            throw new AssertionError("Display name with DST savings should not be null");
        }
        System.out.println("PASS: testGetDisplayNameWithDSTSavings");
    }
}
