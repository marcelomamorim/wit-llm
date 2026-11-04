/* @test    @summary Test TemporalAdjusters.lastInMonth with various days of week    @run main TemporalAdjustersWitupTest */

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.lastInMonth;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjuster;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testLastInMonthWithDifferentDays();
        testLastInMonthEdgeCases();
    }

    static void testLastInMonthWithDifferentDays() {
        // Test for each day of week
        for (DayOfWeek dow : DayOfWeek.values()) {
            TemporalAdjuster adjuster = lastInMonth(dow);
            LocalDate date = LocalDate.of(2023, 1, 1); // arbitrary date
            LocalDate result = date.with(adjuster);
            // Verify that result is the last occurrence of dow in the month
            LocalDate expected = date.withDayOfMonth(date.lengthOfMonth());
            while (expected.getDayOfWeek() != dow) {
                expected = expected.minusDays(1);
            }
            if (!result.equals(expected)) {
                throw new AssertionError("Failed for " + dow + ": expected " + expected + " but got " + result);
            }
        }
    }

    static void testLastInMonthEdgeCases() {
        // Test for February in a leap year
        LocalDate febLeap = LocalDate.of(2020, 2, 1);
        LocalDate lastSunday = febLeap.with(lastInMonth(DayOfWeek.SUNDAY));
        if (lastSunday.getMonthValue() != 2 || lastSunday.getDayOfWeek() != DayOfWeek.SUNDAY || lastSunday.getDayOfMonth() != 29) {
            throw new AssertionError("Failed leap year February last Sunday: " + lastSunday);
        }
        // Test for February in a non-leap year
        LocalDate febNonLeap = LocalDate.of(2021, 2, 1);
        LocalDate lastMonday = febNonLeap.with(lastInMonth(DayOfWeek.MONDAY));
        if (lastMonday.getMonthValue() != 2 || lastMonday.getDayOfWeek() != DayOfWeek.MONDAY || lastMonday.getDayOfMonth() != 22) {
            throw new AssertionError("Failed non-leap year February last Monday: " + lastMonday);
        }
        // Test for end of month with Sunday
        LocalDate endOfMonth = LocalDate.of(2023, 4, 15);
        LocalDate lastSundayInApril = endOfMonth.with(lastInMonth(DayOfWeek.SUNDAY));
        if (lastSundayInApril.getMonthValue() != 4 || lastSundayInApril.getDayOfWeek() != DayOfWeek.SUNDAY || lastSundayInApril.getDayOfMonth() != 30) {
            throw new AssertionError("Failed April last Sunday: " + lastSundayInApril);
        }
    }
}
