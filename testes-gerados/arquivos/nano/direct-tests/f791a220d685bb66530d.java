/* @test    @summary Test TemporalAdjusters.firstInMonth method for various days of week    @run main TemporalAdjustersWitupTest */

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjuster;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testFirstInMonth(MONDAY, 1);
        testFirstInMonth(TUESDAY, 2);
        testFirstInMonth(WEDNESDAY, 3);
        testFirstInMonth(THURSDAY, 4);
        testFirstInMonth(FRIDAY, 5);
        testFirstInMonth(SATURDAY, 6);
        testFirstInMonth(SUNDAY, 7);
    }

    private static void testFirstInMonth(DayOfWeek dow, int expectedDayOfMonth) {
        LocalDate date = LocalDate.of(2023, 3, 15); // Arbitrary date in March 2023
        TemporalAdjuster adjuster = firstInMonth(dow);
        LocalDate resultDate = date.with(adjuster);
        assertEquals("Failed for " + dow, expectedDayOfMonth, resultDate.getDayOfMonth());
        assertEquals("Failed for " + dow, date.getYear(), resultDate.getYear());
        assertEquals("Failed for " + dow, date.getMonth(), resultDate.getMonth());
        assertEquals("Failed for " + dow, dow, resultDate.getDayOfWeek());
    }
}
