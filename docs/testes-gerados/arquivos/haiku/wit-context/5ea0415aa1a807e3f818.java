/*
 * @test
 * @summary Verifies TimeZone.getDisplayName rejects illegal styles and returns non-empty names for valid styles
 * @run main TimeZoneWitupTest
 */
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {
    public static void main(String[] args) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        int[] badStyles = {2, -1, 100};
        for (int style : badStyles) {
            for (boolean dst : new boolean[]{false, true}) {
                boolean thrown = false;
                try {
                    tz.getDisplayName(dst, style, Locale.US);
                } catch (IllegalArgumentException expected) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new RuntimeException("Expected IllegalArgumentException for style " + style + " dst=" + dst);
                }
            }
        }

        int[] validStyles = {TimeZone.SHORT, TimeZone.LONG};
        for (int style : validStyles) {
            String name = tz.getDisplayName(false, style, Locale.US);
            if (name == null || name.isEmpty()) {
                throw new RuntimeException("Null or empty display name for UTC style " + style);
            }
            String dstName = tz.getDisplayName(true, style, Locale.US);
            if (dstName == null || dstName.isEmpty()) {
                throw new RuntimeException("Null or empty DST display name for UTC style " + style);
            }
        }

        TimeZone custom = TimeZone.getTimeZone("GMT+10:00");
        String customName = custom.getDisplayName(false, TimeZone.SHORT, Locale.US);
        if (customName == null || customName.isEmpty()) {
            throw new RuntimeException("Null or empty display name for custom GMT id");
        }
    }
}
