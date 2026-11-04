/*
 * @test
 * @summary Witup-generated checks for TimeZone.getDisplayName(boolean, int) style validation
 * @run main TimeZoneWitupTest
 */

public class TimeZoneWitupTest {
    public static void main(String[] args) {
        TimeZone tz = TimeZone.getTimeZone("GMT");
        boolean[] daylightValues = {false, true};
        int[] validStyles = {TimeZone.SHORT, TimeZone.LONG};
        for (boolean daylight : daylightValues) {
            for (int style : validStyles) {
                String name = tz.getDisplayName(daylight, style);
                if (name == null || name.isEmpty()) {
                    throw new RuntimeException("Empty display name for daylight=" + daylight + ", style=" + style);
                }
            }
        }

        int[] invalidStyles = {2, -1, 3, 4};
        for (int style : invalidStyles) {
            boolean thrown = false;
            try {
                tz.getDisplayName(false, style);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for style " + style);
            }
        }
    }
}
