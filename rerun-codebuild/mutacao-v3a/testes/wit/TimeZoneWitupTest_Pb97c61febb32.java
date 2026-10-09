/*
 * @test
 * @summary Test for TimeZone.getDisplayName method for valid and invalid style parameters.
 * @run main TimeZoneWitupTest_Pb97c61febb32
 */

import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneWitupTest_Pb97c61febb32 {

    public static void main(String[] args) {
        testGetDisplayNameWithValidStyles();
        testGetDisplayNameWithInvalidStyle();
        testGetDisplayNameWithGMTId();
    }

    static void testGetDisplayNameWithValidStyles() {
        try {
            TimeZone tz = TimeZone.getDefault();
            String shortName = tz.getDisplayName(false, TimeZone.SHORT, Locale.US);
            String longName = tz.getDisplayName(true, TimeZone.LONG, Locale.US);
            System.out.println("Valid style test passed: " + shortName + ", " + longName);
        } catch (Exception e) {
            System.err.println("Valid style test failed: " + e);
        }
    }

    static void testGetDisplayNameWithInvalidStyle() {
        try {
            TimeZone tz = TimeZone.getDefault();
            // Passing an invalid style (not SHORT or LONG) should throw IllegalArgumentException
            int invalidStyle = 42;
            tz.getDisplayName(false, invalidStyle, Locale.US);
            System.err.println("Invalid style test failed: Exception was not thrown as expected.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid style test passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Invalid style test failed: Unexpected exception: " + e);
        }
    }

    static void testGetDisplayNameWithGMTId() {
        try {
            TimeZone gmtZone = TimeZone.getTimeZone("GMT+02:00");
            String name = gmtZone.getDisplayName(false, TimeZone.SHORT, Locale.US);
            if (name != null) {
                System.out.println("GMT ID test passed: " + name);
            } else {
                System.err.println("GMT ID test failed: Name is null.");
            }
        } catch (Exception e) {
            System.err.println("GMT ID test failed: " + e);
        }
    }
}
