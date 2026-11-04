/*
 * @test
 * @summary Test java.util.Calendar.Builder.setLocale(Locale) method
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetLocaleWithValidLocale();
        testSetLocaleWithNull();
    }

    private static void testSetLocaleWithValidLocale() {
        Calendar.Builder builder = new Calendar.Builder();
        Locale locale = Locale.US;
        Calendar.Builder returned = builder.setLocale(locale);
        if (returned != builder) {
            throw new AssertionError("setLocale should return the same Builder instance");
        }
    }

    private static void testSetLocaleWithNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setLocale(null);
            throw new AssertionError("setLocale(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
