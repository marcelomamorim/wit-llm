/*
 * @test
 * @summary Test for Calendar.setTimeZone with null input
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetTimeZoneWithNull();
        testSetTimeZoneWithValid();
    }

    public static void testSetTimeZoneWithNull() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTimeZone(null);
            // If no exception, test fails
            System.err.println("Failed: Expected NullPointerException for null TimeZone");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("Passed: NullPointerException thrown as expected");
        }
    }

    public static void testSetTimeZoneWithValid() {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        try {
            cal.setTimeZone(tz);
            if (cal.getTimeZone().equals(tz)) {
                System.out.println("Passed: TimeZone set correctly");
            } else {
                System.err.println("Failed: TimeZone not set correctly");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
