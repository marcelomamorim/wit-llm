/*
 * @test
 * @summary Witup generated tests for MonthDay.with(Month)
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testNullMonthThrowsNPE();
        testSameMonthReturnsThis();
        testDayClampedToFebruary();
        testDayClampedToApril();
        testDayKeptWhenFits();
    }

    static void testNullMonthThrowsNPE() {
        MonthDay md = MonthDay.of(1, 31);
        try {
            md.with((Month) null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testSameMonthReturnsThis() {
        MonthDay md = MonthDay.of(3, 10);
        MonthDay result = md.with(Month.MARCH);
        if (result != md) {
            throw new AssertionError("expected same instance when month is unchanged");
        }
    }

    static void testDayClampedToFebruary() {
        MonthDay md = MonthDay.of(1, 31);
        MonthDay result = md.with(Month.FEBRUARY);
        check(result, 2, 29);
    }

    static void testDayClampedToApril() {
        MonthDay md = MonthDay.of(1, 31);
        MonthDay result = md.with(Month.APRIL);
        check(result, 4, 30);
    }

    static void testDayKeptWhenFits() {
        MonthDay md = MonthDay.of(1, 15);
        MonthDay result = md.with(Month.MARCH);
        check(result, 3, 15);
    }

    static void check(MonthDay actual, int expectedMonth, int expectedDay) {
        if (actual.getMonthValue() != expectedMonth || actual.getDayOfMonth() != expectedDay) {
            throw new AssertionError("expected --" + expectedMonth + "-" + expectedDay
                    + " but was " + actual);
        }
    }
}
