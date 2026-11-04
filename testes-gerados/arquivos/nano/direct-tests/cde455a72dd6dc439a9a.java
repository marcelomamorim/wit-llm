/*
 * @test
 * @summary Tests for MonthDay.of(Month, int)
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.time.DateTimeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testValidMonthDay();
        testNullMonth();
        testInvalidDayTooLarge();
        testInvalidDayZero();
        testInvalidDayNegative();
    }

    static void testValidMonthDay() {
        Month month = Month.JANUARY;
        int day = 15;
        MonthDay md = MonthDay.of(month, day);
        assert md.getMonthValue() == month.getValue() : "Month value mismatch";
        assert md.getDayOfMonth() == day : "Day of month mismatch";
    }

    static void testNullMonth() {
        try {
            MonthDay.of(null, 10);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidDayTooLarge() {
        try {
            MonthDay.of(Month.FEBRUARY, 30);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayZero() {
        try {
            MonthDay.of(Month.MARCH, 0);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDayNegative() {
        try {
            MonthDay.of(Month.APRIL, -5);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
