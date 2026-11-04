/*
 * @test
 * @summary Test java.time.MonthDay.of(Month, int) method for valid and invalid inputs
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testValidMonthDay();
        testNullMonth();
        testInvalidDayZero();
        testInvalidDayTooLarge();
        testDayExceedsMonthMaxLength();
    }

    private static void testValidMonthDay() {
        MonthDay md = MonthDay.of(Month.JANUARY, 15);
        if (md.getMonth() != Month.JANUARY) {
            throw new AssertionError("Expected month JANUARY but got " + md.getMonth());
        }
        if (md.getDayOfMonth() != 15) {
            throw new AssertionError("Expected day 15 but got " + md.getDayOfMonth());
        }
    }

    private static void testNullMonth() {
        try {
            MonthDay.of(null, 10);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testInvalidDayZero() {
        try {
            MonthDay.of(Month.JANUARY, 0);
            throw new AssertionError("Expected DateTimeException for day 0");
        } catch (DateTimeException dte) {
            // expected
        }
    }

    private static void testInvalidDayTooLarge() {
        try {
            MonthDay.of(Month.JANUARY, 32);
            throw new AssertionError("Expected DateTimeException for day 32");
        } catch (DateTimeException dte) {
            // expected
        }
    }

    private static void testDayExceedsMonthMaxLength() {
        // February max length is 29
        try {
            MonthDay.of(Month.FEBRUARY, 30);
            throw new AssertionError("Expected DateTimeException for day 30 in February");
        } catch (DateTimeException dte) {
            // expected
        }
    }
}
