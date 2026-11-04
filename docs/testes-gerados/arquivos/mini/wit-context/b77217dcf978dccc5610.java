/*
 * @test
 * @summary Test java.util.TimeZone.getDisplayName(boolean, int) for valid and invalid style values
 * @run main TimeZoneWitupTest
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest {
    public static void main(String[] args) {
        TimeZone tz = TimeZone.getDefault();

        // Test valid style values: 0 (SHORT) and 1 (LONG)
        String shortName = tz.getDisplayName(false, 0);
        if (shortName == null || shortName.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for style=0");
        }

        String longName = tz.getDisplayName(true, 1);
        if (longName == null || longName.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for style=1");
        }

        // Test invalid style values: expect IllegalArgumentException
        int[] invalidStyles = {-1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int style : invalidStyles) {
            boolean threw = false;
            try {
                tz.getDisplayName(false, style);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("Expected IllegalArgumentException for style=" + style);
            }
        }

        System.out.println("All tests passed.");
    }
}
