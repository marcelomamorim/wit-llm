/*
 * @test
 * @summary Test java.time.Year.atMonthDay(MonthDay) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.MonthDay;
import java.time.LocalDate;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtMonthDayBasic();
        testAtMonthDayLeapYear();
    }

    private static void testAtMonthDayBasic() {
        Year y = Year.of(2020);
        MonthDay md = MonthDay.of(5, 15); // May 15
        LocalDate ld = y.atMonthDay(md);
        if (ld.getYear() != 2020) {
            throw new AssertionError("Year mismatch: expected 2020 but got " + ld.getYear());
        }
        if (ld.getMonthValue() != 5) {
            throw new AssertionError("Month mismatch: expected 5 but got " + ld.getMonthValue());
        }
        if (ld.getDayOfMonth() != 15) {
            throw new AssertionError("Day mismatch: expected 15 but got " + ld.getDayOfMonth());
        }
    }

    private static void testAtMonthDayLeapYear() {
        Year y = Year.of(2020); // Leap year
        MonthDay md = MonthDay.of(2, 29); // Feb 29
        LocalDate ld = y.atMonthDay(md);
        if (ld.getYear() != 2020) {
            throw new AssertionError("Year mismatch: expected 2020 but got " + ld.getYear());
        }
        if (ld.getMonthValue() != 2) {
            throw new AssertionError("Month mismatch: expected 2 but got " + ld.getMonthValue());
        }
        if (ld.getDayOfMonth() != 29) {
            throw new AssertionError("Day mismatch: expected 29 but got " + ld.getDayOfMonth());
        }
    }
}
