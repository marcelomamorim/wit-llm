/* @test    @summary Test for MonthDay.atYear(int) method to verify behavior and exception handling    @run main MonthDayWitupTest */

import java.time.MonthDay;
import java.time.LocalDate;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testValidYear();
        testYearLessThanMinimum();
        testYearGreaterThanMaximum();
        testYearWithinRange();
        testInvalidMonth();
        testInvalidDay();
    }

    private static void testValidYear() {
        try {
            MonthDay md = MonthDay.of(2, 29);
            LocalDate date = md.atYear(2020);
            if (!date.equals(LocalDate.of(2020, 2, 29))) {
                throw new AssertionError("Expected date 2020-02-29, got " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid year: " + e);
        }
    }

    private static void testYearLessThanMinimum() {
        try {
            MonthDay md = MonthDay.of(3, 15);
            md.atYear(Integer.MIN_VALUE);
            throw new AssertionError("Expected exception for year less than minimum");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException, got: " + e);
            }
        }
    }

    private static void testYearGreaterThanMaximum() {
        try {
            MonthDay md = MonthDay.of(4, 10);
            md.atYear(Integer.MAX_VALUE);
            // Should succeed, as no explicit max year, but check for potential issues
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for max year: " + e);
        }
    }

    private static void testYearWithinRange() {
        try {
            MonthDay md = MonthDay.of(5, 20);
            LocalDate date = md.atYear(2018);
            if (!date.equals(LocalDate.of(2018, 5, 20))) {
                throw new AssertionError("Expected date 2018-05-20, got " + date);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid year: " + e);
        }
    }

    private static void testInvalidMonth() {
        try {
            MonthDay.of(13, 10);
            throw new AssertionError("Expected exception for invalid month");
        } catch (Exception e) {
            if (!(e instanceof java.time.DateTimeException)) {
                throw new AssertionError("Expected DateTimeException, got: " + e);
            }
        }
    }

    private static void testInvalidDay() {
        try {
            MonthDay.of(2, 30);
            // Should succeed because MonthDay.of does not validate day, but atYear may throw
            MonthDay md = MonthDay.of(2, 30);
            md.atYear(2021);
            // No exception expected here, as MonthDay.of does not validate day
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid day: " + e);
        }
    }
}
