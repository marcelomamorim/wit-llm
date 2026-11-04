/*
 * @test
 * @summary Test for LocalDate.withDayOfYear(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithDayOfYearValid();
        testWithDayOfYearInvalid();
        testWithDayOfYearSameValue();
    }

    static void testWithDayOfYearValid() {
        LocalDate date = LocalDate.of(2021, 3, 15);
        int validDayOfYear = 74; // 15th March in non-leap year
        LocalDate result = date.withDayOfYear(validDayOfYear);
        assert result.getYear() == 2021 : "Year should remain unchanged";
        assert result.getDayOfYear() == validDayOfYear : "Day of year should match input";
    }

    static void testWithDayOfYearInvalid() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        try {
            // Invalid day of year: 366 in a non-leap year
            date.withDayOfYear(366);
            assert false : "Expected DateTimeException for invalid day of year";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    static void testWithDayOfYearSameValue() {
        LocalDate date = LocalDate.of(2020, 2, 29); // leap year
        int dayOfYear = date.getDayOfYear();
        LocalDate result = date.withDayOfYear(dayOfYear);
        assert result == date : "Should return same instance if dayOfYear is same";
    }
}
