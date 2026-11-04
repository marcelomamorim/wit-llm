/*
 * @test
 * @summary Witup-derived tests for Calendar.Builder.setLocale(Locale)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
        testValidLocaleReturnsSameBuilder();
        testBuildAfterSetLocale();
    }

    static void testNullLocaleThrowsNPE() {
        Calendar.Builder builder = new Calendar.Builder();
        boolean thrown = false;
        try {
            builder.setLocale(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    static void testValidLocaleReturnsSameBuilder() {
        Calendar.Builder builder = new Calendar.Builder();
        Calendar.Builder result = builder.setLocale(Locale.US);
        if (result != builder) {
            throw new RuntimeException("setLocale should return the same Builder instance");
        }
    }

    static void testBuildAfterSetLocale() {
        Calendar cal = new Calendar.Builder()
                .setLocale(Locale.US)
                .setDate(2020, Calendar.JANUARY, 15)
                .build();
        if (cal == null) {
            throw new RuntimeException("build() returned null");
        }
        if (cal.get(Calendar.YEAR) != 2020) {
            throw new RuntimeException("Unexpected YEAR: " + cal.get(Calendar.YEAR));
        }
        if (cal.get(Calendar.MONTH) != Calendar.JANUARY) {
            throw new RuntimeException("Unexpected MONTH: " + cal.get(Calendar.MONTH));
        }
    }
}
