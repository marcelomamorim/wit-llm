/*
 * @test
 * @summary Test java.util.Calendar.Builder.setTimeZone(TimeZone) method
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
        Calendar.Builder builder = new Calendar.Builder();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Calendar.Builder ret = builder.setTimeZone(tz);
        if (ret != builder) {
            throw new RuntimeException("setTimeZone should return the same Builder instance");
        }
    }

    static void testSetTimeZoneWithNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setTimeZone(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
