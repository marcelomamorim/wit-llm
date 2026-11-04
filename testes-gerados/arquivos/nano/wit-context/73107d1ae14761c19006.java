/*
 * @test
 * @summary Tests for java.time.Year.atDay(int)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.LocalDate;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtDayValid();
        testAtDayInvalidBelow();
        testAtDayInvalidAbove();
    }

    static void testAtDayValid() {
        Year year = Year.of(2020);
        int dayOfYear = 60; // Feb 29, 2020 (leap year)
        LocalDate date = year.atDay(dayOfYear);
        assert date.getYear() == 2020 : "Expected year 2020";
        assert date.getDayOfYear() == dayOfYear : "Expected day of year 60";
    }

    static void testAtDayInvalidBelow() {
        Year year = Year.of(2021);
        int invalidDay = 0; // invalid day (less than 1)
        try {
            year.atDay(invalidDay);
            assert false : "Expected DateTimeException for day 0";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testAtDayInvalidAbove() {
        Year year = Year.of(2021);
        int maxDay = year.length(); // 365
        int invalidDay = maxDay + 1; // invalid day (greater than length)
        try {
            year.atDay(invalidDay);
            assert false : "Expected DateTimeException for day " + invalidDay;
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
