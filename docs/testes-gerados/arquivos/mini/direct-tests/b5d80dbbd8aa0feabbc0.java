/*
 * @test
 * @summary Test java.time.LocalDate.minusMonths(long) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testMinusMonthsPositive();
        testMinusMonthsNegative();
        testMinusMonthsLongMinValue();
    }

    private static void testMinusMonthsPositive() {
        LocalDate date = LocalDate.of(2019, Month.MARCH, 31);
        LocalDate result = date.minusMonths(1);
        // Expect 2019-02-28 because Feb 31 is invalid, so last valid day of Feb
        if (!result.equals(LocalDate.of(2019, Month.FEBRUARY, 28))) {
            throw new AssertionError("Expected 2019-02-28 but got " + result);
        }
    }

    private static void testMinusMonthsNegative() {
        LocalDate date = LocalDate.of(2019, Month.JANUARY, 31);
        LocalDate result = date.minusMonths(-1);
        // minusMonths(-1) is plusMonths(1), expect 2019-02-28
        if (!result.equals(LocalDate.of(2019, Month.FEBRUARY, 28))) {
            throw new AssertionError("Expected 2019-02-28 but got " + result);
        }
    }

    private static void testMinusMonthsLongMinValue() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 15);
        // minusMonths(Long.MIN_VALUE) internally calls plusMonths(Long.MAX_VALUE).plusMonths(1)
        LocalDate result = date.minusMonths(Long.MIN_VALUE);
        // We test that result is not null and is a valid LocalDate
        if (result == null) {
            throw new AssertionError("Result should not be null for minusMonths(Long.MIN_VALUE)");
        }
        // We test that result is different from original date (very likely)
        if (result.equals(date)) {
            throw new AssertionError("Result should differ from original date for minusMonths(Long.MIN_VALUE)");
        }
    }
}
