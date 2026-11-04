/*
 * @test
 * @summary Tests for java.time.LocalDateTime.of method, focusing on exception paths.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullMonth();
        testYearTooSmall();
        testYearTooLarge();
        testDayOfMonthInvalid();
    }

    private static void testNullMonth() {
        try {
            LocalDateTime.of(2020, null, 15, 10, 30, 20, 500000000);
            System.err.println("Failed: Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testYearTooSmall() {
        int minYear = -999999999;
        int maxYear = 999999999;
        try {
            LocalDateTime.of(minYear - 1, Month.JANUARY, 10, 10, 10, 10, 0);
            System.err.println("Failed: Expected DateTimeException for year below minimum");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
    }

    private static void testYearTooLarge() {
        int minYear = -999999999;
        int maxYear = 999999999;
        try {
            LocalDateTime.of(maxYear + 1, Month.JANUARY, 10, 10, 10, 10, 0);
            System.err.println("Failed: Expected DateTimeException for year above maximum");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
    }

    private static void testDayOfMonthInvalid() {
        try {
            LocalDateTime.of(2020, Month.FEBRUARY, 30, 10, 10, 10, 0);
            System.err.println("Failed: Expected DateTimeException for invalid day of month");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
        try {
            LocalDateTime.of(2020, Month.APRIL, 31, 10, 10, 10, 0);
            System.err.println("Failed: Expected DateTimeException for invalid day of month");
        } catch (java.time.DateTimeException e) {
            // Expected
        }
    }
}
