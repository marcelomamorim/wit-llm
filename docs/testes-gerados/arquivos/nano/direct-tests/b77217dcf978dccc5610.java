/* @test    @summary Test getDisplayName with daylight true and false, style short and long    @run main TimeZoneWitupTest */

import java.util.TimeZone;

public class TimeZoneWitupTest {

    public static void main(String[] args) {
        testGetDisplayName(true, 0);  // style short
        testGetDisplayName(true, 1);  // style long
        testGetDisplayName(false, 0); // style short
        testGetDisplayName(false, 1); // style long
    }

    private static void testGetDisplayName(boolean daylight, int style) {
        TimeZone tz = TimeZone.getDefault();
        String name = tz.getDisplayName(daylight, style);
        System.out.println("DisplayName (daylight=" + daylight + ", style=" + style + "): " + name);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty string for daylight=" + daylight + " style=" + style);
        }
    }
}
