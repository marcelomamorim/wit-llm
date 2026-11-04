/* @test    @summary Test TimeZone.getDisplayName with various parameters    @run main TimeZoneWitupTest */

import java.util.TimeZone;
import java.util.Locale;

public class TimeZoneWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameStandard();
        testGetDisplayNameWithGMTId();
        testGetDisplayNameInvalidStyle();
    }

    static void testGetDisplayNameStandard() {
        TimeZone tz = TimeZone.getDefault();
        String name = tz.getDisplayName(true, TimeZone.LONG, Locale.US);
        String nameShort = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        System.out.println("Standard long name: " + name);
        System.out.println("Standard short name: " + nameShort);
        assert name != null : "Display name should not be null";
        assert nameShort != null : "Short display name should not be null";
    }

    static void testGetDisplayNameWithGMTId() {
        TimeZone gmtTz = TimeZone.getTimeZone("GMT+2");
        String name = gmtTz.getDisplayName(true, TimeZone.LONG, Locale.US);
        String nameShort = gmtTz.getDisplayName(false, TimeZone.SHORT, Locale.US);
        System.out.println("GMT+2 long name: " + name);
        System.out.println("GMT+2 short name: " + nameShort);
        assert name != null : "Display name for GMT+2 should not be null";
        assert nameShort != null : "Short display name for GMT+2 should not be null";
    }

    static void testGetDisplayNameInvalidStyle() {
        try {
            TimeZone tz = TimeZone.getDefault();
            tz.getDisplayName(true, 999, Locale.US);
            assert false : "Expected IllegalArgumentException for invalid style";
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception for invalid style: " + e.getMessage());
        }
    }
}
