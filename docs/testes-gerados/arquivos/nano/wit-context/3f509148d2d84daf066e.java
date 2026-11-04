/*
 * @test
 * @summary Tests for java.time.temporal.WeekFields.of(Locale)
 * @run main WeekFieldsWitupTest
 */

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekFieldsWitupTest {

    public static void main(String[] args) {
        testOfWithNullLocale();
        testOfWithValidLocales();
    }

    public static void testOfWithNullLocale() {
        try {
            WeekFields.of(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
            System.out.println("NullPointerException correctly thrown for null locale");
        }
    }

    public static void testOfWithValidLocales() {
        Locale[] locales = {Locale.US, Locale.FRANCE, Locale.JAPAN};
        for (Locale locale : locales) {
            try {
                WeekFields wf = WeekFields.of(locale);
                if (wf == null) {
                    System.err.println("WeekFields.of returned null for locale: " + locale);
                    System.exit(1);
                } else {
                    System.out.println("WeekFields.of succeeded for locale: " + locale);
                }
            } catch (Exception e) {
                System.err.println("Unexpected exception for locale: " + locale + ": " + e);
                System.exit(1);
            }
        }
    }
}
