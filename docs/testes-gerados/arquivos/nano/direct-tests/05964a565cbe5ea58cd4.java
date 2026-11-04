/*
 * @test
 * @summary Test YearMonth.atDay(int) method for valid and invalid inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.LocalDate;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testAtDayValid();
        testAtDayInvalid();
    }

    static void testAtDayValid() {
        YearMonth ym = YearMonth.of(2023, 3); // March 2023
        LocalDate date = ym.atDay(15);
        assert date.getYear() == 2023 : "Year mismatch";
        assert date.getMonthValue() == 3 : "Month mismatch";
        assert date.getDayOfMonth() == 15 : "Day mismatch";
    }

    static void testAtDayInvalid() {
        YearMonth ym = YearMonth.of(2023, 2); // February 2023
        try {
            ym.atDay(29); // 2023 is not a leap year
            assert false : "Expected DateTimeException for invalid day";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
        try {
            ym.atDay(0); // Day zero is invalid
            assert false : "Expected DateTimeException for day zero";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
        try {
            ym.atDay(32); // Day beyond max for month
            assert false : "Expected DateTimeException for day beyond max";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
