/*
 * @test
 * @summary Test for YearMonth.of(int, Month) method, covering normal and exceptional cases.
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.util.Objects;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNullMonth();
        testInvalidYear();
    }

    static void testValidInputs() {
        int year = 2023;
        Month month = Month.APRIL;
        YearMonth ym = YearMonth.of(year, month);
        if (ym == null) {
            throw new AssertionError("Expected non-null YearMonth for valid inputs");
        }
        if (ym.getYear() != year) {
            throw new AssertionError("Year mismatch: expected " + year + " but got " + ym.getYear());
        }
        if (ym.getMonth() != month) {
            throw new AssertionError("Month mismatch: expected " + month + " but got " + ym.getMonth());
        }
    }

    static void testNullMonth() {
        int year = 2023;
        try {
            YearMonth.of(year, (Month) null);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testInvalidYear() {
        int invalidYear = Integer.MIN_VALUE;
        Month month = Month.JANUARY;
        try {
            YearMonth.of(invalidYear, month);
            // No exception expected here, as YearMonth.of does not validate year range
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid year: " + e);
        }
    }
}
