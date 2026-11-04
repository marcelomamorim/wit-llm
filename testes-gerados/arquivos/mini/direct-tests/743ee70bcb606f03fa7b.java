/*
 * @test
 * @summary Test java.time.LocalDate.of(int, Month, int) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.DateTimeException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testValidDate();
        testNullMonth();
        testInvalidYear();
        testInvalidDayOfMonth();
    }

    private static void testValidDate() {
        LocalDate date = LocalDate.of(2018, Month.SEPTEMBER, 30);
        if (date.getYear() != 2018) throw new AssertionError("Year mismatch");
        if (date.getMonth() != Month.SEPTEMBER) throw new AssertionError("Month mismatch");
        if (date.getDayOfMonth() != 30) throw new AssertionError("Day mismatch");
    }

    private static void testNullMonth() {
        try {
            LocalDate.of(2018, null, 15);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException npe) {
            // expected
            if (!"month".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testInvalidYear() {
        try {
            // YEAR.checkValidValue(year) throws DateTimeException if invalid
            // Use an invalid year outside the supported range
            LocalDate.of(Integer.MIN_VALUE, Month.JANUARY, 1);
            throw new AssertionError("Expected DateTimeException for invalid year");
        } catch (java.time.DateTimeException dte) {
            // expected
        }
    }

    private static void testInvalidDayOfMonth() {
        try {
            // DAY_OF_MONTH.checkValidValue(dayOfMonth) throws DateTimeException if invalid
            LocalDate.of(2018, Month.JANUARY, 0); // day 0 invalid
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (java.time.DateTimeException dte) {
            // expected
        }

        try {
            LocalDate.of(2018, Month.JANUARY, 32); // day 32 invalid
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (java.time.DateTimeException dte) {
            // expected
        }
    }
}
