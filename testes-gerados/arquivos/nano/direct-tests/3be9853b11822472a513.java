/*
 * @test
 * @summary Test LocalDate.withYear(int year) method for correctness and regressions
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import static java.time.LocalDate.of;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithYearSameYear();
        testWithYearDifferentYear();
        testWithYearInvalid();
    }

    static void testWithYearSameYear() {
        LocalDate date = of(2020, 5, 15);
        LocalDate result = date.withYear(2020);
        if (result != date) {
            throw new AssertionError("Expected same instance when year is unchanged");
        }
    }

    static void testWithYearDifferentYear() {
        LocalDate date = of(2020, 5, 15);
        LocalDate result = date.withYear(2021);
        if (result.equals(date)) {
            throw new AssertionError("Expected different instance when year changes");
        }
        if (result.getYear() != 2021) {
            throw new AssertionError("Year not updated correctly");
        }
        if (result.getMonthValue() != date.getMonthValue() || result.getDayOfMonth() != date.getDayOfMonth()) {
            throw new AssertionError("Month or day changed unexpectedly");
        }
    }

    static void testWithYearInvalid() {
        LocalDate date = of(2020, 2, 29);
        try {
            date.withYear(2019); // 2019 is not leap year, should throw exception or handle invalid date
            throw new AssertionError("Expected exception for invalid date")
        } catch (Exception e) {
            // Expected exception, test passes
        }
    }
}
