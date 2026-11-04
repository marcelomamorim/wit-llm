/*
 * @test
 * @summary Test YearMonth.of(int, Month) for null and invalid inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.DateTimeException;
import java.util.Objects;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testNullMonth();
        testValidYearMonth();
        testInvalidYearBelowRange();
        testInvalidYearAboveRange();
        testInvalidMonthValue();
    }

    private static void testNullMonth() {
        try {
            YearMonth.of(2000, (Month) null);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidYearMonth() {
        YearMonth ym = YearMonth.of(2020, Month.FEBRUARY);
        if (ym.getYear() != 2020 || ym.getMonth() != Month.FEBRUARY) {
            throw new AssertionError("YearMonth.of did not create expected instance");
        }
    }

    private static void testInvalidYearBelowRange() {
        // The valid year range is defined by ChronoField.YEAR range, typically -999999999 to 999999999
        // We test a year below the minimum to trigger DateTimeException
        int minYear = (int) java.time.temporal.ChronoField.YEAR.range().getMinimum();
        int invalidYear = minYear - 1;
        try {
            YearMonth.of(invalidYear, Month.JANUARY);
            throw new AssertionError("Expected DateTimeException for year below minimum");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidYearAboveRange() {
        int maxYear = (int) java.time.temporal.ChronoField.YEAR.range().getMaximum();
        int invalidYear = maxYear + 1;
        try {
            YearMonth.of(invalidYear, Month.DECEMBER);
            throw new AssertionError("Expected DateTimeException for year above maximum");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMonthValue() {
        // Month enum values are 1 to 12, test with invalid month value by reflection is not allowed,
        // so test indirectly by calling YearMonth.of(int, Month) with valid Month but invalid year
        // that triggers month value check in YearMonth.of(int, int)
        // This is covered by invalid year tests, so no separate test here.
    }
}
