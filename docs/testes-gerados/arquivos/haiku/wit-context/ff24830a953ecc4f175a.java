/*
 * @test
 * @summary Verifies SimpleTimeZone.setDSTSavings rejects non-positive daylight saving values and accepts positive ones
 * @run main SimpleTimeZoneWitupTest
 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest {

    public static void main(String[] args) {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");

        expectIllegalArgument(tz, 0);
        expectIllegalArgument(tz, -1);
        expectIllegalArgument(tz, -3600000);
        expectIllegalArgument(tz, Integer.MIN_VALUE);

        tz.setDSTSavings(1);
        tz.setDSTSavings(3600000);
        tz.setDSTSavings(Integer.MAX_VALUE);

        expectIllegalArgument(tz, 0);
        tz.setDSTSavings(1800000);
    }

    private static void expectIllegalArgument(SimpleTimeZone tz, int value) {
        try {
            tz.setDSTSavings(value);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for millisSavedDuringDST=" + value);
    }
}
