/*
 * @test
 * @summary Test java.time.Year.atMonthDay(MonthDay) method behavior
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.MonthDay;
import java.time.LocalDate;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtMonthDayValid();
        testAtMonthDayNull();
        testAtMonthDayInvalidMonthDay();
    }

    private static void testAtMonthDayValid() {
        Year y = Year.of(2020); // leap year
        MonthDay md = MonthDay.of(2, 29); // valid leap day
        LocalDate ld = y.atMonthDay(md);
        if (ld.getYear() != 2020 || ld.getMonthValue() != 2 || ld.getDayOfMonth() != 29) {
            throw new AssertionError("Expected 2020-02-29 but got " + ld);
        }

        y = Year.of(2019); // non-leap year
        md = MonthDay.of(3, 1);
        ld = y.atMonthDay(md);
        if (ld.getYear() != 2019 || ld.getMonthValue() != 3 || ld.getDayOfMonth() != 1) {
            throw new AssertionError("Expected 2019-03-01 but got " + ld);
        }
    }

    private static void testAtMonthDayNull() {
        Year y = Year.of(2000);
        try {
            y.atMonthDay(null);
            throw new AssertionError("Expected NullPointerException for null MonthDay");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAtMonthDayInvalidMonthDay() {
        // MonthDay.of throws DateTimeException for invalid month/day, so test with valid MonthDay but invalid year
        // The method atMonthDay calls monthDay.atYear(year), which may throw DateTimeException if year is out of range
        // We test with extreme year values to provoke DateTimeException

        MonthDay md = MonthDay.of(2, 29); // valid leap day

        // Year.of throws DateTimeException if year out of range, so use Year.of with valid year but test atMonthDay with extreme year
        // Instead, create Year with valid year and test atMonthDay with MonthDay that is valid but year that causes failure

        // Since Year.atMonthDay uses the stored year, we test with Year.of with extreme values
        // Year.MIN_VALUE = -999_999_999, Year.MAX_VALUE = 999_999_999

        // Test with Year.MIN_VALUE and MonthDay 2-29 (should throw DateTimeException because MIN_VALUE is not leap year)
        Year y = Year.of(Year.MIN_VALUE);
        try {
            y.atMonthDay(md);
            throw new AssertionError("Expected DateTimeException for invalid date with year MIN_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }

        // Test with Year.MAX_VALUE and MonthDay 2-29 (should throw DateTimeException because MAX_VALUE is not leap year)
        y = Year.of(Year.MAX_VALUE);
        try {
            y.atMonthDay(md);
            throw new AssertionError("Expected DateTimeException for invalid date with year MAX_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
