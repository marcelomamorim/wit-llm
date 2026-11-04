/*
 * @test
 * @summary Verifies Calendar.Builder.setLocale returns the same builder, rejects null with NullPointerException, and still allows build.
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {
    public static void main(String[] args) {
        Calendar.Builder builder = new Calendar.Builder();

        Calendar.Builder returned = builder.setLocale(Locale.US);
        if (returned != builder) {
            throw new RuntimeException("setLocale must return the same Builder instance");
        }

        boolean thrown = false;
        try {
            builder.setLocale(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("setLocale(null) must throw NullPointerException");
        }

        Calendar.Builder chained = builder.setLocale(Locale.JAPAN);
        if (chained != builder) {
            throw new RuntimeException("setLocale must return the same Builder instance after a null attempt");
        }

        Calendar calendar = builder.build();
        if (calendar == null) {
            throw new RuntimeException("build must return a non-null Calendar");
        }
    }
}
