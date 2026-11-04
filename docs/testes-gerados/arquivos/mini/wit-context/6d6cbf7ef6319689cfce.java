/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.plusYears(long)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DateTimeException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testPlusYearsZero();
        testPlusYearsNormal();
        testPlusYearsOverflowLow();
        testPlusYearsOverflowHigh();
    }

    private static void testPlusYearsZero() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalDate result = date.plusYears(0);
        if (result != date) {
            throw new AssertionError("plusYears(0) should return same instance");
        }
    }

    private static void testPlusYearsNormal() {
        LocalDate date = LocalDate.of(2000, 2, 29);
        LocalDate result = date.plusYears(1);
        // 2001 is not a leap year, so day should be adjusted to 28
        if (result.getYear() != 2001 || result.getMonthValue() != 2 || result.getDayOfMonth() != 28) {
            throw new AssertionError("plusYears(1) failed to adjust leap day correctly: " + result);
        }
    }

    private static void testPlusYearsOverflowLow() {
        LocalDate date = LocalDate.of(1, 1, 1);
        try {
            // Adding a large negative number to cause year underflow
            date.plusYears(-1000000000L);
            throw new AssertionError("Expected DateTimeException for year underflow not thrown");
        } catch (DateTimeException e) {
            // Expected
        }
    }

    private static void testPlusYearsOverflowHigh() {
        LocalDate date = LocalDate.of(999999999, 12, 31);
        try {
            // Adding a large positive number to cause year overflow
            date.plusYears(1000000000L);
            throw new AssertionError("Expected DateTimeException for year overflow not thrown");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
