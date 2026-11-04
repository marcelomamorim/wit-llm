/*
 * @test
 * @summary Test java.time.LocalDate.withYear(int) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testWithYearSameYearReturnsThis();
        testWithYearValidChange();
        testWithYearInvalidLow();
        testWithYearInvalidHigh();
    }

    static void testWithYearSameYearReturnsThis() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 15);
        LocalDate result = date.withYear(2018);
        if (result != date) {
            throw new AssertionError("Expected same instance when year is unchanged");
        }
    }

    static void testWithYearValidChange() {
        LocalDate date = LocalDate.of(2018, Month.MARCH, 31);
        LocalDate changed = date.withYear(2019);
        // 2019-03-31 is valid
        if (changed.getYear() != 2019 || changed.getMonth() != Month.MARCH || changed.getDayOfMonth() != 31) {
            throw new AssertionError("Expected date with year changed to 2019 and same month/day");
        }
    }

    static void testWithYearInvalidLow() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        try {
            date.withYear(-1); // year < YEAR.MIN_VALUE (which is -999_999_999)
            throw new AssertionError("Expected DateTimeException for year -1");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testWithYearInvalidHigh() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        try {
            date.withYear(1000000000); // year > YEAR.MAX_VALUE (which is 999_999_999)
            throw new AssertionError("Expected DateTimeException for year 1000000000");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
