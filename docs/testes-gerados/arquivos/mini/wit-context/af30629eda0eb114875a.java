/*
 * @test
 * @summary Test TemporalAdjusters.lastInMonth(DayOfWeek) for null argument and normal usage
 * @run main TemporalAdjustersWitupTest
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TemporalAdjustersWitupTest {
    public static void main(String[] args) {
        testLastInMonthWithNull();
        testLastInMonthWithValidDayOfWeek();
    }

    private static void testLastInMonthWithNull() {
        try {
            TemporalAdjusters.lastInMonth(null);
            throw new AssertionError("Expected NullPointerException for null DayOfWeek");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testLastInMonthWithValidDayOfWeek() {
        TemporalAdjuster adjuster = TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY);
        LocalDate date = LocalDate.of(2018, 9, 15); // arbitrary date
        LocalDate adjusted = date.with(adjuster);
        // The adjusted date must be a Monday and in the last week of the month
        if (adjusted.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new AssertionError("Adjusted date is not Monday: " + adjusted);
        }
        LocalDate nextWeek = adjusted.plusDays(7);
        if (nextWeek.getMonth() == adjusted.getMonth()) {
            throw new AssertionError("Adjusted date is not the last Monday of the month: " + adjusted);
        }
    }
}
