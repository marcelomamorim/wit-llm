/*
 * @test
 * @summary Test LocalDate.plusYears(long) method for correctness and regressions
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import static java.time.LocalDate.of;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsPositive();
        testPlusYearsNegative();
        testPlusYearsOverflow();
    }

    static void testPlusYearsZero() {
        LocalDate date = of(2020, 1, 1);
        LocalDate result = date.plusYears(0);
        if (!result.equals(date)) {
            throw new AssertionError("plusYears(0) should return the same date");
        }
    }

    static void testPlusYearsPositive() {
        LocalDate date = of(2020, 2, 29);
        LocalDate result = date.plusYears(1);
        LocalDate expected = of(2021, 2, 28);
        if (!result.equals(expected)) {
            throw new AssertionError("plusYears(1) did not produce expected date");
        }
    }

    static void testPlusYearsNegative() {
        LocalDate date = of(2020, 2, 29);
        LocalDate result = date.plusYears(-1);
        LocalDate expected = of(2019, 2, 28);
        if (!result.equals(expected)) {
            throw new AssertionError("plusYears(-1) did not produce expected date");
        }
    }

    static void testPlusYearsOverflow() {
        try {
            LocalDate date = of(9999, 12, 31);
            date.plusYears(1000);
            throw new AssertionError("Expected overflow or exception for year overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
