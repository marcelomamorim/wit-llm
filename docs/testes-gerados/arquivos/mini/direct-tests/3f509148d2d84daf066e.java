/*
 * @test
 * @summary Test WeekFields.of(Locale) method behavior
 * @run main WeekFieldsWitupTest
 */

import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;

public class WeekFieldsWitupTest {
    public static void main(String[] args) {
        testOfWithValidLocale();
        testOfWithNullLocale();
    }

    private static void testOfWithValidLocale() {
        Locale locale = Locale.US;
        WeekFields wf = WeekFields.of(locale);
        Objects.requireNonNull(wf);
        // The first day of week for US locale is SUNDAY
        DayOfWeek firstDay = wf.getFirstDayOfWeek();
        if (firstDay != DayOfWeek.SUNDAY) {
            throw new AssertionError("Expected first day of week SUNDAY but got " + firstDay);
        }
        int minDays = wf.getMinimalDaysInFirstWeek();
        if (minDays < 1 || minDays > 7) {
            throw new AssertionError("Minimal days in first week out of range: " + minDays);
        }
    }

    private static void testOfWithNullLocale() {
        try {
            WeekFields.of(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
