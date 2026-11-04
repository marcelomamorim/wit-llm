/*
 * @test
 * @summary Verifies Calendar.Builder.setTimeZone returns the builder for a non-null zone and rejects null with NullPointerException
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarWitupTest {
    public static void main(String[] args) {
        Calendar.Builder builder = new Calendar.Builder();
        TimeZone utc = TimeZone.getTimeZone("UTC");

        Calendar.Builder returned = builder.setTimeZone(utc);
        if (returned != builder) {
            throw new RuntimeException("setTimeZone must return the same Builder instance");
        }

        boolean thrown = false;
        try {
            builder.setTimeZone(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setTimeZone(null) must throw NullPointerException");
        }

        Calendar.Builder afterFailure = builder.setTimeZone(utc);
        if (afterFailure != builder) {
            throw new RuntimeException("Builder must remain usable after rejected null zone");
        }
    }
}
