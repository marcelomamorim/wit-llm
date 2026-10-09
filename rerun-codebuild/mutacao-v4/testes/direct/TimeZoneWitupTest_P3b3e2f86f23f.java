/* @test
   @summary Tests the getDisplayName method of java.util.TimeZone.
   @run main TimeZoneWitupTest_P3b3e2f86f23f
 */
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_P3b3e2f86f23f {
    public static void main(String[] args) {
        testGetDisplayNameWithValidStyles();
        testGetDisplayNameWithInvalidStyle();
        testGetDisplayNameWithGMTId();
        testGetDisplayNameWithOffsetId();
    }

    static void testGetDisplayNameWithValidStyles() {
        TimeZone tz = TimeZone.getDefault();
        String nameLong = tz.getDisplayName(true, java.util.TimeZone.LONG, Locale.US);
        String nameShort = tz.getDisplayName(false, java.util.TimeZone.SHORT, Locale.US);
        assert nameLong != null : "Long style display name should not be null";
        assert nameShort != null : "Short style display name should not be null";
    }

    static void testGetDisplayNameWithInvalidStyle() {
        try {
            TimeZone.getDefault().getDisplayName(true, 999, Locale.US);
            throw new AssertionError("Expected IllegalArgumentException for invalid style");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testGetDisplayNameWithGMTId() {
        TimeZone gmtTz = TimeZone.getTimeZone("GMT+02:00");
        String displayName = gmtTz.getDisplayName(true, java.util.TimeZone.LONG, Locale.US);
        assert displayName != null : "Display name for GMT+02:00 should not be null";
        assert displayName.contains("GMT") : "Display name should contain 'GMT'";
    }

    static void testGetDisplayNameWithOffsetId() {
        // Create a TimeZone with an ID that starts with 'GMT' and a custom offset
        TimeZone customTz = new TimeZone() {
            @Override
            public String getID() {
                return "GMT+05:30";
            }
            @Override
            public int getRawOffset() {
                return 19800000; // 5 hours 30 minutes in milliseconds
            }
            @Override
            public int getDSTSavings() {
                return 0;
            }
        };
        String displayName = customTz.getDisplayName(true, java.util.TimeZone.LONG, Locale.US);
        assert displayName != null : "Display name for custom GMT+05:30 should not be null";
        assert displayName.contains("GMT") : "Display name should contain 'GMT'";
    }
}
