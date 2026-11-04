/*
 * @test
 * @summary Test for Calendar.setLocale(Locale)
 * @run main CalendarWitupTest
 */

import java.util.Calendar;
import java.util.Locale;

public class CalendarWitupTest {

    public static void main(String[] args) {
        testSetLocaleWithNull();
        testSetLocaleWithValidLocale();
    }

    public static void testSetLocaleWithNull() {
        Calendar.Builder builder = new Calendar.Builder();
        try {
            builder.setLocale(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testSetLocaleWithValidLocale() {
        Calendar.Builder builder = new Calendar.Builder();
        Locale locale = Locale.US;
        try {
            builder.setLocale(locale);
            System.out.println("PASS: setLocale with valid Locale succeeded");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
