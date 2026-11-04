/*
 * @test
 * @summary Test Year.atDay(int) method for valid and invalid inputs
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.LocalDate;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtDayValid();
        testAtDayInvalid();
    }

    static void testAtDayValid() {
        Year year = Year.of(2020);
        LocalDate date = year.atDay(60);
        assert date.getYear() == 2020 : "Year should be 2020";
        assert date.getDayOfYear() == 60 : "Day of year should be 60";
    }

    static void testAtDayInvalid() {
        Year year = Year.of(2020);
        try {
            year.atDay(366); // 2020 is leap year, so 366 is valid
            year.atDay(367); // invalid, should throw exception
            assert false : "Expected exception for invalid day of year";
        } catch (Exception e) {
            assert e instanceof java.time.DateTimeException : "Expected DateTimeException";
        }
        Year nonLeapYear = Year.of(2019);
        try {
            nonLeapYear.atDay(366); // invalid, 2019 is not leap year
            assert false : "Expected exception for invalid day of year";
        } catch (Exception e) {
            assert e instanceof java.time.DateTimeException : "Expected DateTimeException";
        }
    }
}
