/*
 * @test
 * @summary Deterministic tests for java.time.Year.atDay(int) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.LocalDate;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testValidDayOfYear();
        testInvalidDayOfYearTooLow();
        testInvalidDayOfYearTooHigh();
    }

    private static void testValidDayOfYear() {
        Year y = Year.of(2000); // Leap year
        LocalDate ld = y.atDay(60); // Feb 29 in leap year
        if (ld.getYear() != 2000 || ld.getDayOfYear() != 60) {
            throw new AssertionError("Expected year 2000 and dayOfYear 60, got " + ld);
        }
        // Also test first and last valid day
        ld = y.atDay(1);
        if (ld.getDayOfYear() != 1) {
            throw new AssertionError("Expected dayOfYear 1, got " + ld);
        }
        ld = y.atDay(366);
        if (ld.getDayOfYear() != 366) {
            throw new AssertionError("Expected dayOfYear 366, got " + ld);
        }
    }

    private static void testInvalidDayOfYearTooLow() {
        Year y = Year.of(2001); // Non-leap year
        try {
            y.atDay(0); // dayOfYear too low
            throw new AssertionError("Expected DateTimeException for dayOfYear=0");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static void testInvalidDayOfYearTooHigh() {
        Year y = Year.of(2001); // Non-leap year
        try {
            y.atDay(366); // dayOfYear too high for non-leap year
            throw new AssertionError("Expected DateTimeException for dayOfYear=366 in non-leap year");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
