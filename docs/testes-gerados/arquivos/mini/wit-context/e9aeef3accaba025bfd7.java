/*
 * @test
 * @summary Test java.time.Year.atMonth(Month) method for valid and exceptional cases
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.Month;
import java.time.YearMonth;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testAtMonthValid();
        testAtMonthNull();
        testAtMonthDateTimeException();
    }

    private static void testAtMonthValid() {
        Year y = Year.of(2020);
        Month m = Month.JANUARY;
        YearMonth ym = y.atMonth(m);
        if (ym.getYear() != 2020 || ym.getMonth() != m) {
            throw new AssertionError("YearMonth returned by atMonth has incorrect year or month");
        }
    }

    private static void testAtMonthNull() {
        Year y = Year.of(2020);
        try {
            y.atMonth(null);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAtMonthDateTimeException() {
        // The method calls YearMonth.of(year, month) which validates the year and month values.
        // To provoke DateTimeException, we try a year outside the valid range for YearMonth.of or a month value invalid.
        // Since Year.of(int) accepts any int, but YearMonth.of(int, Month) validates the year range,
        // we test with a year outside YearMonth range (YearMonth supports years from MIN_VALUE to MAX_VALUE, but
        // YearMonth.of checks year range via ChronoField.YEAR.checkValidValue, which throws DateTimeException if invalid).
        // The valid year range for YearMonth is from Year.MIN_VALUE (-999_999_999) to Year.MAX_VALUE (999_999_999),
        // so to trigger DateTimeException, we use a year outside this range.

        // However, Year.of(int) does not restrict year range, but YearMonth.of(int, Month) will throw DateTimeException
        // if year is out of range.

        // Test with year less than YearMonth.MIN_YEAR - 1
        int invalidYearLow = -1_000_000_000; // less than Year.MIN_VALUE
        try {
            Year yLow = Year.of(invalidYearLow);
            yLow.atMonth(Month.JANUARY);
            throw new AssertionError("Expected DateTimeException for year less than Year.MIN_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }

        // Test with year greater than YearMonth.MAX_YEAR + 1
        int invalidYearHigh = 1_000_000_000; // greater than Year.MAX_VALUE
        try {
            Year yHigh = Year.of(invalidYearHigh);
            yHigh.atMonth(Month.DECEMBER);
            throw new AssertionError("Expected DateTimeException for year greater than Year.MAX_VALUE");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
