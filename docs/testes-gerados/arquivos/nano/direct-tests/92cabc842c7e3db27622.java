/*
 * @test
 * @summary Test Calendar.setLocale(Locale) method for null and valid inputs
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetLocaleWithNull();
        testSetLocaleWithValidLocale();
    }

    private static void testSetLocaleWithNull() {
        Calendar cal = Calendar.getInstance();
        boolean exceptionThrown = false;
        try {
            cal.setLocale(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when setting null locale");
        }
    }

    private static void testSetLocaleWithValidLocale() {
        Calendar cal = Calendar.getInstance();
        Locale locale = Locale.FRANCE;
        cal.setLocale(locale);
        if (!cal.getLocale().equals(locale)) {
            throw new AssertionError("Locale not set correctly. Expected: " + locale + ", but got: " + cal.getLocale());
        }
    }
}
