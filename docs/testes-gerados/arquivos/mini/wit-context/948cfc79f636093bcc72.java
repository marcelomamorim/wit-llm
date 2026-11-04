/*
 * @test
 * @summary Test java.util.Calendar.Builder.setTimeZone(TimeZone) for null and valid inputs
 * @run main CalendarWitupTest
 */

import java.util.TimeZone;
import java.util.Calendar.Builder;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetTimeZoneWithNull();
        testSetTimeZoneWithValidZone();
    }

    private static void testSetTimeZoneWithNull() {
        Builder builder = new Builder();
        try {
            builder.setTimeZone(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSetTimeZoneWithValidZone() {
        Builder builder = new Builder();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Builder returned = builder.setTimeZone(tz);
        if (returned != builder) {
            throw new RuntimeException("setTimeZone should return the same Builder instance");
        }
    }
}
