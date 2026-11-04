/*
 * @test
 * @summary Test for TemporalAdjusters.lastInMonth(DayOfWeek)
 * @run main TemporalAdjustersWitupTest
 */

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testLastInMonthWithValidDayOfWeek();
        testLastInMonthWithNullDayOfWeek();
    }

    private static void testLastInMonthWithValidDayOfWeek() {
        DayOfWeek dow = DayOfWeek.MONDAY;
        java.time.temporal.TemporalAdjuster adjuster = java.time.temporal.TemporalAdjusters.lastInMonth(dow);
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalDate result = date.with(adjuster);
        // Expect the last Monday of March 2023
        LocalDate expected = LocalDate.of(2023, 3, 27);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testLastInMonthWithNullDayOfWeek() {
        try {
            java.time.temporal.TemporalAdjusters.lastInMonth(null);
            throw new AssertionError("Expected NullPointerException for null DayOfWeek");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
