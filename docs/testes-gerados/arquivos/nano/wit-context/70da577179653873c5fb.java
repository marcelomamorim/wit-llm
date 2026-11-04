/*
 * @test
 * @summary Tests for java.time.Year.atMonth(int)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.YearMonth;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthValid();
        testAtMonthInvalidTooLow();
        testAtMonthInvalidTooHigh();
    }

    static void testAtMonthValid() {
        Year year = Year.of(2020);
        int validMonth = 5;
        YearMonth ym = year.atMonth(validMonth);
        if (ym.getYear() != 2020 || ym.getMonthValue() != validMonth) {
            throw new AssertionError("Valid atMonth did not produce expected YearMonth");
        }
    }

    static void testAtMonthInvalidTooLow() {
        Year year = Year.of(2020);
        int invalidMonth = 0; // invalid month, should throw
        try {
            year.atMonth(invalidMonth);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testAtMonthInvalidTooHigh() {
        Year year = Year.of(2020);
        int invalidMonth = 13; // invalid month, should throw
        try {
            year.atMonth(invalidMonth);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
