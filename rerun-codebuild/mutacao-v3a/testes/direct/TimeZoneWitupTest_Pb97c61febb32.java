/* @test    @summary Test getDisplayName with different styles and locales in TimeZone  @run main TimeZoneWitupTest_Pb97c61febb32 */

import java.util.TimeZone;
import java.util.Locale;

public class TimeZoneWitupTest_Pb97c61febb32 {

    public static void main(String[] args) {
        testGetDisplayNameWithShortStyle();
        testGetDisplayNameWithLongStyle();
        testGetDisplayNameWithInvalidStyle();
        testGetDisplayNameWithUnknownID();
    }

    static void testGetDisplayNameWithShortStyle() {
        TimeZone tz = TimeZone.getDefault();
        String name = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
        System.out.println("Short daylight display name: " + name);
        assert name != null : "Display name should not be null for short style";
    }

    static void testGetDisplayNameWithLongStyle() {
        TimeZone tz = TimeZone.getDefault();
        String name = tz.getDisplayName(false, TimeZone.LONG, Locale.US);
        System.out.println("Long standard display name: " + name);
        assert name != null : "Display name should not be null for long style";
    }

    static void testGetDisplayNameWithInvalidStyle() {
        try {
            TimeZone tz = TimeZone.getDefault();
            tz.getDisplayName(true, 999, Locale.US);
            assert false : "Expected IllegalArgumentException for invalid style";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for invalid style: " + e.getMessage());
        }
    }

    static void testGetDisplayNameWithUnknownID() {
        TimeZone tz = TimeZone.getTimeZone("Unknown/Zone");
        String name = tz.getDisplayName(true, TimeZone.SHORT, Locale.US);
        System.out.println("Display name for unknown ID: " + name);
        assert name != null : "Display name should not be null for unknown ID";
    }
}
