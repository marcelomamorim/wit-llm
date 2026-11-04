/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.of(int, Month, int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testValidDateCreation();
        testNullMonthThrowsNPE();
        testYearOutOfRangeThrowsDateTimeException();
        testDayOfMonthOutOfRangeThrowsDateTimeException();
        System.out.println("All tests passed.");
    }

    private static void testValidDateCreation() {
        LocalDate date = LocalDate.of(2018, Month.JANUARY, 1);
        if (date.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (date.getMonth() != Month.JANUARY) throw new AssertionError("Month mismatch");
        if (date.getDayOfMonth() != 1) throw new AssertionError("Day mismatch");
    }

    private static void testNullMonthThrowsNPE() {
        try {
            LocalDate.of(2018, null, 1);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException ex) {
            // Expected
        }
    }

    private static void testYearOutOfRangeThrowsDateTimeException() {
        // YEAR field valid range is -999999999 to 999999999
        int tooLowYear = -1000000000;
        int tooHighYear = 1000000000;
        try {
            LocalDate.of(tooLowYear, Month.JANUARY, 1);
            throw new AssertionError("Expected DateTimeException for year too low");
        } catch (DateTimeException ex) {
            // Expected
        }
        try {
            LocalDate.of(tooHighYear, Month.JANUARY, 1);
            throw new AssertionError("Expected DateTimeException for year too high");
        } catch (DateTimeException ex) {
            // Expected
        }
    }

    private static void testDayOfMonthOutOfRangeThrowsDateTimeException() {
        // January has 31 days, test day 0 and day 32
        try {
            LocalDate.of(2018, Month.JANUARY, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth 0");
        } catch (DateTimeException ex) {
            // Expected
        }
        try {
            LocalDate.of(2018, Month.JANUARY, 32);
            throw new AssertionError("Expected DateTimeException for dayOfMonth 32");
        } catch (DateTimeException ex) {
            // Expected
        }
    }
}
