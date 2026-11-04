/*
 * @test
 * @summary Test TemporalAdjusters.lastInMonth(DayOfWeek) returns correct adjuster
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testLastInMonthWithMonday();
        testLastInMonthWithSunday();
        testLastInMonthWithWednesday();
    }

    private static void testLastInMonthWithMonday() {
        TemporalAdjuster lastMonday = TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY);
        LocalDate date = LocalDate.of(2021, 3, 15); // arbitrary date in March 2021
        LocalDate adjusted = date.with(lastMonday);
        // The last Monday in March 2021 is 29th
        if (!adjusted.equals(LocalDate.of(2021, 3, 29))) {
            throw new AssertionError("Expected last Monday in March 2021 to be 2021-03-29 but was " + adjusted);
        }
    }

    private static void testLastInMonthWithSunday() {
        TemporalAdjuster lastSunday = TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY);
        LocalDate date = LocalDate.of(2021, 2, 10); // arbitrary date in February 2021
        LocalDate adjusted = date.with(lastSunday);
        // The last Sunday in February 2021 is 28th
        if (!adjusted.equals(LocalDate.of(2021, 2, 28))) {
            throw new AssertionError("Expected last Sunday in February 2021 to be 2021-02-28 but was " + adjusted);
        }
    }

    private static void testLastInMonthWithWednesday() {
        TemporalAdjuster lastWednesday = TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY);
        LocalDate date = LocalDate.of(2021, 8, 1); // arbitrary date in August 2021
        LocalDate adjusted = date.with(lastWednesday);
        // The last Wednesday in August 2021 is 25th
        if (!adjusted.equals(LocalDate.of(2021, 8, 25))) {
            throw new AssertionError("Expected last Wednesday in August 2021 to be 2021-08-25 but was " + adjusted);
        }
    }
}
