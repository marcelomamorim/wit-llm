/*
 * @test
 * @summary Witup-generated tests for TemporalAdjusters.firstInMonth
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testFirstMondayOfFebruary2024();
        testFirstSundayOfFebruary2024();
        testFirstMondayWhenMonthStartsOnMonday();
        testNullDayOfWeekThrowsNpe();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFirstMondayOfFebruary2024() {
        TemporalAdjuster adjuster = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
        LocalDate result = LocalDate.of(2024, 2, 15).with(adjuster);
        check(result.equals(LocalDate.of(2024, 2, 5)),
              "first Monday of February 2024 expected 2024-02-05 but was " + result);
    }

    static void testFirstSundayOfFebruary2024() {
        TemporalAdjuster adjuster = TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY);
        LocalDate result = LocalDate.of(2024, 2, 15).with(adjuster);
        check(result.equals(LocalDate.of(2024, 2, 4)),
              "first Sunday of February 2024 expected 2024-02-04 but was " + result);
    }

    static void testFirstMondayWhenMonthStartsOnMonday() {
        TemporalAdjuster adjuster = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
        LocalDate result = LocalDate.of(2024, 1, 15).with(adjuster);
        check(result.equals(LocalDate.of(2024, 1, 1)),
              "first Monday of January 2024 expected 2024-01-01 but was " + result);
    }

    static void testNullDayOfWeekThrowsNpe() {
        try {
            TemporalAdjusters.firstInMonth(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null dayOfWeek");
    }
}
