/*
 * @test
 * @summary Test Year.atMonthDay(MonthDay) method for correct LocalDate output.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.MonthDay;
import java.time.LocalDate;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthDayWithValidMonthDay();
        testAtMonthDayWithEdgeMonths();
        testAtMonthDayWithInvalidMonthDay();
    }

    static void testAtMonthDayWithValidMonthDay() {
        Year year = Year.of(2021);
        MonthDay md = MonthDay.of(12, 25); // December 25
        LocalDate expectedDate = LocalDate.of(2021, 12, 25);
        LocalDate result = year.atMonthDay(md);
        if (!result.equals(expectedDate)) {
            throw new AssertionError("Expected: " + expectedDate + ", but got: " + result);
        }
    }

    static void testAtMonthDayWithEdgeMonths() {
        Year year = Year.of(2020); // leap year
        MonthDay md1 = MonthDay.of(2, 29); // Feb 29 in leap year
        LocalDate expectedDate1 = LocalDate.of(2020, 2, 29);
        LocalDate result1 = year.atMonthDay(md1);
        if (!result1.equals(expectedDate1)) {
            throw new AssertionError("Expected: " + expectedDate1 + ", but got: " + result1);
        }
        Year yearNonLeap = Year.of(2019); // non-leap year
        MonthDay md2 = MonthDay.of(2, 28); // Feb 28
        LocalDate expectedDate2 = LocalDate.of(2019, 2, 28);
        LocalDate result2 = yearNonLeap.atMonthDay(md2);
        if (!result2.equals(expectedDate2)) {
            throw new AssertionError("Expected: " + expectedDate2 + ", but got: " + result2);
        }
    }

    static void testAtMonthDayWithInvalidMonthDay() {
        Year year = Year.of(2021);
        try {
            MonthDay invalidMd = MonthDay.of(13, 1); // invalid month
            year.atMonthDay(invalidMd);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (RuntimeException e) {
            // Expected exception
        }
        try {
            MonthDay invalidMd2 = MonthDay.of(0, 10); // invalid month
            year.atMonthDay(invalidMd2);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (RuntimeException e) {
            // Expected exception
        }
    }
}
