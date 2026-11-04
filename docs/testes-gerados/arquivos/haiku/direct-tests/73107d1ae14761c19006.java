/*
 * @test
 * @summary Checks Year.atDay(int) for valid day-of-year values and for out-of-range values
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        check(Year.of(2023).atDay(1), LocalDate.of(2023, 1, 1));
        check(Year.of(2023).atDay(365), LocalDate.of(2023, 12, 31));
        check(Year.of(2024).atDay(60), LocalDate.of(2024, 2, 29));
        check(Year.of(2024).atDay(366), LocalDate.of(2024, 12, 31));
        check(Year.of(-1).atDay(1), LocalDate.of(-1, 1, 1));
        check(Year.of(2023).atDay(32), LocalDate.of(2023, 2, 1));

        expectDateTimeException(Year.of(2023), 0);
        expectDateTimeException(Year.of(2023), 366);
        expectDateTimeException(Year.of(2023), 367);
        expectDateTimeException(Year.of(2024), 367);
    }

    private static void check(LocalDate actual, LocalDate expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    private static void expectDateTimeException(Year year, int dayOfYear) {
        try {
            LocalDate result = year.atDay(dayOfYear);
            throw new RuntimeException("Expected DateTimeException for year " + year
                    + " and dayOfYear " + dayOfYear + " but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
