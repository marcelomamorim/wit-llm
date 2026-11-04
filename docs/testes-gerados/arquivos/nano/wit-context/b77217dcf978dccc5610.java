/* @test    @summary Test getDisplayName with valid parameters and invalid style    @run main TimeZoneWitupTest */

import java.util.TimeZone;

public class TimeZoneWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameValid();
        testGetDisplayNameInvalidStyle();
    }

    static void testGetDisplayNameValid() {
        TimeZone tz = TimeZone.getDefault();
        String nameDaylight = tz.getDisplayName(true, 0);
        String nameStandard = tz.getDisplayName(false, 1);
        if (nameDaylight == null || nameStandard == null) {
            throw new AssertionError("Expected non-null display names for valid styles");
        }
    }

    static void testGetDisplayNameInvalidStyle() {
        TimeZone tz = TimeZone.getDefault();
        try {
            tz.getDisplayName(true, 2); // invalid style
            throw new AssertionError("Expected IllegalArgumentException for style=2");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            tz.getDisplayName(false, -1); // invalid style
            throw new AssertionError("Expected IllegalArgumentException for style=-1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
