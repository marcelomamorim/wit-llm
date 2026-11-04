/*
 * @test
 * @summary Test for Calendar.setTimeZone(TimeZone)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetTimeZoneWithValidZone();
        testSetTimeZoneWithNull();
    }

    static void testSetTimeZoneWithValidZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone originalZone = cal.getTimeZone();
        TimeZone newZone = TimeZone.getTimeZone("GMT+2");
        cal.setTimeZone(newZone);
        assert cal.getTimeZone().equals(newZone) : "TimeZone should be updated to GMT+2";
        // Reset to original to avoid side effects
        cal.setTimeZone(originalZone);
    }

    static void testSetTimeZoneWithNull() {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTimeZone(null);
            assert false : "NullPointerException expected when setting null TimeZone";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
