/*
 * @test
 * @summary Test java.time.temporal.TemporalAdjusters.firstInMonth(DayOfWeek) method
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testFirstInMonthReturnsFirstMonday();
        testFirstInMonthReturnsFirstSunday();
        testFirstInMonthReturnsFirstWednesday();
    }

    private static void testFirstInMonthReturnsFirstMonday() {
        TemporalAdjuster firstMonday = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
        LocalDate date = LocalDate.of(2021, 3, 15); // arbitrary date in March 2021
        LocalDate adjusted = date.with(firstMonday);
        // The first Monday in March 2021 is March 1
        if (!adjusted.equals(LocalDate.of(2021, 3, 1))) {
            throw new AssertionError("Expected 2021-03-01 but got " + adjusted);
        }
    }

    private static void testFirstInMonthReturnsFirstSunday() {
        TemporalAdjuster firstSunday = TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY);
        LocalDate date = LocalDate.of(2021, 8, 20); // arbitrary date in August 2021
        LocalDate adjusted = date.with(firstSunday);
        // The first Sunday in August 2021 is August 1
        if (!adjusted.equals(LocalDate.of(2021, 8, 1))) {
            throw new AssertionError("Expected 2021-08-01 but got " + adjusted);
        }
    }

    private static void testFirstInMonthReturnsFirstWednesday() {
        TemporalAdjuster firstWednesday = TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY);
        LocalDate date = LocalDate.of(2021, 9, 10); // arbitrary date in September 2021
        LocalDate adjusted = date.with(firstWednesday);
        // The first Wednesday in September 2021 is September 1
        if (!adjusted.equals(LocalDate.of(2021, 9, 1))) {
            throw new AssertionError("Expected 2021-09-01 but got " + adjusted);
        }
    }
}
