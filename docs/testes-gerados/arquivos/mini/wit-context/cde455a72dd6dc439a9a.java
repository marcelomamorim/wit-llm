/*
 * @test
 * @summary Test java.time.MonthDay.of(Month, int) for valid and invalid inputs
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testNullMonthThrowsNPE();
        testInvalidDayTooLowThrowsDateTimeException();
        testInvalidDayTooHighThrowsDateTimeException();
        testDayExceedsMonthMaxLengthThrowsDateTimeException();
        testValidMonthDayCreatesInstance();
    }

    private static void testNullMonthThrowsNPE() {
        try {
            MonthDay.of(null, 1);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException ex) {
            // expected
            if (!"month".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", ex);
            }
        }
    }

    private static void testInvalidDayTooLowThrowsDateTimeException() {
        try {
            MonthDay.of(Month.JANUARY, 0); // dayOfMonth < 1 invalid
            throw new AssertionError("Expected DateTimeException for dayOfMonth=0");
        } catch (DateTimeException ex) {
            // expected
        }
    }

    private static void testInvalidDayTooHighThrowsDateTimeException() {
        try {
            MonthDay.of(Month.JANUARY, 32); // dayOfMonth > maxLargest (31) invalid
            throw new AssertionError("Expected DateTimeException for dayOfMonth=32");
        } catch (DateTimeException ex) {
            // expected
        }
    }

    private static void testDayExceedsMonthMaxLengthThrowsDateTimeException() {
        // February maxLength is 29 (leap year considered), so 30 is invalid
        try {
            MonthDay.of(Month.FEBRUARY, 30);
            throw new AssertionError("Expected DateTimeException for dayOfMonth=30 in February");
        } catch (DateTimeException ex) {
            // expected
        }
    }

    private static void testValidMonthDayCreatesInstance() {
        MonthDay md = MonthDay.of(Month.DECEMBER, 25);
        if (md.getMonth() != Month.DECEMBER) {
            throw new AssertionError("Month mismatch");
        }
        if (md.getDayOfMonth() != 25) {
            throw new AssertionError("DayOfMonth mismatch");
        }
    }
}
