/*
 * @test
 * @summary Tests MonthDay.with(Month) clamping, same-month identity and null handling
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testClampsDayToNewMonthLength();
        testLeapDayClampedToFebruary();
        testChangeWithoutClampingKeepsDay();
        testSameMonthReturnsSameInstance();
        testNullMonthThrowsNullPointerException();
    }

    static void testClampsDayToNewMonthLength() {
        MonthDay md = MonthDay.of(1, 31);
        MonthDay result = md.with(Month.FEBRUARY);
        check(result.getMonth() == Month.FEBRUARY, "month should be FEBRUARY");
        check(result.getDayOfMonth() == 29, "day should be clamped to 29, was " + result.getDayOfMonth());
        check(result.equals(MonthDay.of(2, 29)), "result should equal --02-29");

        MonthDay april = md.with(Month.APRIL);
        check(april.getDayOfMonth() == 30, "day should be clamped to 30 for APRIL");
        check(april.equals(MonthDay.of(4, 30)), "result should equal --04-30");
    }

    static void testLeapDayClampedToFebruary() {
        MonthDay leap = MonthDay.of(2, 29);
        MonthDay result = leap.with(Month.APRIL);
        check(result.getDayOfMonth() == 29, "day 29 should remain 29 in APRIL (max 30)");
        check(result.equals(MonthDay.of(4, 29)), "result should equal --04-29");

        MonthDay dec = MonthDay.of(12, 31).with(Month.NOVEMBER);
        check(dec.getDayOfMonth() == 30, "day should be clamped to 30 for NOVEMBER");
    }

    static void testChangeWithoutClampingKeepsDay() {
        MonthDay md = MonthDay.of(4, 10);
        MonthDay result = md.with(Month.JUNE);
        check(result.getMonthValue() == 6, "month value should be 6");
        check(result.getDayOfMonth() == 10, "day should remain 10");
        check(result.equals(MonthDay.of(6, 10)), "result should equal --06-10");
    }

    static void testSameMonthReturnsSameInstance() {
        MonthDay md = MonthDay.of(3, 15);
        MonthDay result = md.with(Month.MARCH);
        check(result == md, "same month should return this");

        MonthDay leap = MonthDay.of(2, 29);
        check(leap.with(Month.FEBRUARY) == leap, "same month FEBRUARY should return this");
    }

    static void testNullMonthThrowsNullPointerException() {
        MonthDay md = MonthDay.of(5, 5);
        try {
            md.with((Month) null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
