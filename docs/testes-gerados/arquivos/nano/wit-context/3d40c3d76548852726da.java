/*
 * @test
 * @summary Test for LocalDate.minusYears(long) method, covering normal and edge cases.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testMinusYearsNormal();
        testMinusYearsLargeValues();
        testMinusYearsEdgeCases();
    }

    static void testMinusYearsNormal() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);
        LocalDate result = date.minusYears(5);
        if (!result.equals(LocalDate.of(2015, Month.JANUARY, 1))) {
            throw new AssertionError("Failed: minusYears(5)");
        }
        result = date.minusYears(0);
        if (!result.equals(date)) {
            throw new AssertionError("Failed: minusYears(0)");
        }
    }

    static void testMinusYearsLargeValues() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        // Test with Long.MIN_VALUE
        try {
            date.minusYears(Long.MIN_VALUE);
            // Expect no exception, result is valid date
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MIN_VALUE", e);
        }
        // Test with Long.MAX_VALUE
        try {
            date.minusYears(Long.MAX_VALUE);
            // Expect no exception, result is valid date
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for Long.MAX_VALUE", e);
        }
    }

    static void testMinusYearsEdgeCases() {
        LocalDate date = LocalDate.of(1, Month.JANUARY, 1);
        // Subtracting 1 year from year 1
        LocalDate result = date.minusYears(1);
        if (!result.equals(LocalDate.of(0, Month.JANUARY, 1))) {
            throw new AssertionError("Failed: minusYears(1) from year 1");
        }
        // Subtracting 0 years
        result = date.minusYears(0);
        if (!result.equals(date)) {
            throw new AssertionError("Failed: minusYears(0)");
        }
    }
}
