/*
 * @test
 * @summary WeekFields.of(Locale) must derive first day and minimal days from locale data and reject null
 * @run main WeekFieldsWitupTest
 */
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekFieldsWitupTest {
    public static void main(String[] args) {
        WeekFields us = WeekFields.of(Locale.US);
        check(us.getFirstDayOfWeek() == DayOfWeek.SUNDAY, "US first day should be SUNDAY");
        check(us.getMinimalDaysInFirstWeek() == 1, "US minimal days should be 1");
        check(us.equals(WeekFields.of(DayOfWeek.SUNDAY, 1)), "US must equal SUNDAY/1");

        WeekFields uk = WeekFields.of(Locale.UK);
        check(uk.getFirstDayOfWeek() == DayOfWeek.MONDAY, "UK first day should be MONDAY");
        check(uk.getMinimalDaysInFirstWeek() == 4, "UK minimal days should be 4");
        check(uk.equals(WeekFields.of(DayOfWeek.MONDAY, 4)), "UK must equal MONDAY/4");

        WeekFields de = WeekFields.of(Locale.GERMANY);
        check(de.getFirstDayOfWeek() == DayOfWeek.MONDAY, "Germany first day should be MONDAY");
        check(de.getMinimalDaysInFirstWeek() == 4, "Germany minimal days should be 4");

        boolean npe = false;
        try {
            WeekFields.of((Locale) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null locale must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
