/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.of(int,int,int) to check valid and invalid inputs
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testValidDate();
        testInvalidYearLow();
        testInvalidYearHigh();
        testInvalidMonthLow();
        testInvalidMonthHigh();
        testInvalidDayLow();
        testInvalidDayHigh();
        System.out.println("All tests passed.");
    }

    private static void testValidDate() {
        LocalDate ld = LocalDate.of(2018, 9, 30);
        if (ld.getYear() != 2018 || ld.getMonthValue() != 9 || ld.getDayOfMonth() != 30) {
            throw new AssertionError("Valid date fields do not match");
        }
    }

    private static void testInvalidYearLow() {
        try {
            LocalDate.of(Integer.MIN_VALUE, 1, 1);
            throw new AssertionError("Expected DateTimeException for year too low");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidYearHigh() {
        try {
            LocalDate.of(Integer.MAX_VALUE, 1, 1);
            throw new AssertionError("Expected DateTimeException for year too high");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMonthLow() {
        try {
            LocalDate.of(2018, 0, 1);
            throw new AssertionError("Expected DateTimeException for month too low");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidMonthHigh() {
        try {
            LocalDate.of(2018, 13, 1);
            throw new AssertionError("Expected DateTimeException for month too high");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDayLow() {
        try {
            LocalDate.of(2018, 1, 0);
            throw new AssertionError("Expected DateTimeException for day too low");
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void testInvalidDayHigh() {
        try {
            LocalDate.of(2018, 1, 32);
            throw new AssertionError("Expected DateTimeException for day too high");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
