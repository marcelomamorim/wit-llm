/*
 * @test
 * @summary Witup-style tests for Year.atDay(int) day-of-year handling
 * @run main YearWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;

public class YearWitupTest {
    public static void main(String[] args) {
        checkDate(2019, 1, LocalDate.of(2019, 1, 1));
        checkDate(2019, 365, LocalDate.of(2019, 12, 31));
        checkDate(2020, 60, LocalDate.of(2020, 2, 29));
        checkDate(2020, 366, LocalDate.of(2020, 12, 31));
        checkInvalid(2019, 0);
        checkInvalid(2019, -1);
        checkInvalid(2019, 366);
        checkInvalid(2019, 367);
        checkInvalid(2020, 367);
    }

    static void checkDate(int year, int dayOfYear, LocalDate expected) {
        LocalDate actual = Year.of(year).atDay(dayOfYear);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Year " + year + " day " + dayOfYear
                    + ": expected " + expected + " but was " + actual);
        }
    }

    static void checkInvalid(int year, int dayOfYear) {
        try {
            LocalDate result = Year.of(year).atDay(dayOfYear);
            throw new RuntimeException("Year " + year + " day " + dayOfYear
                    + ": expected DateTimeException but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
