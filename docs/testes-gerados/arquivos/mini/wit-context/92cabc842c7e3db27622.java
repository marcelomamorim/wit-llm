/*
 * @test
 * @summary Test java.util.Calendar.Builder.setLocale(Locale) for null argument and normal usage
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {
    public static void main(String[] args) {
        testSetLocaleNull();
        testSetLocaleValid();
        System.out.println("CalendarWitupTest passed.");
    }

    private static void testSetLocaleNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setLocale(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testSetLocaleValid() {
        Calendar.Builder builder = new Calendar.Builder();
        Locale locale = Locale.US;
        Calendar.Builder returned = builder.setLocale(locale);
        if (returned != builder) {
            throw new RuntimeException("setLocale should return the same Builder instance");
        }
    }
}
