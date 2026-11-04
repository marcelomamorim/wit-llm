/*
 * @test
 * @summary Behavioral tests for MonthDay.of(Month, int) validation and results
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testValidFebruary29();
        testValidDecember31();
        testFebruary30Rejected();
        testApril31Rejected();
        testDayZeroRejected();
        testDay32Rejected();
        testNullMonthRejected();
        testEqualsIntOverload();
    }

    static void testValidFebruary29() {
        MonthDay md = MonthDay.of(Month.FEBRUARY, 29);
        check(md.getMonthValue() == 2, "month value should be 2");
        check(md.getDayOfMonth() == 29, "day should be 29");
    }

    static void testValidDecember31() {
        MonthDay md = MonthDay.of(Month.DECEMBER, 31);
        check(md.getMonthValue() == 12, "month value should be 12");
        check(md.getDayOfMonth() == 31, "day should be 31");
    }

    static void testFebruary30Rejected() {
        expectDateTimeException(Month.FEBRUARY, 30);
    }

    static void testApril31Rejected() {
        expectDateTimeException(Month.APRIL, 31);
    }

    static void testDayZeroRejected() {
        expectDateTimeException(Month.JANUARY, 0);
    }

    static void testDay32Rejected() {
        expectDateTimeException(Month.JANUARY, 32);
    }

    static void testNullMonthRejected() {
        try {
            MonthDay.of((Month) null, 1);
            throw new RuntimeException("expected NullPointerException for null month");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEqualsIntOverload() {
        MonthDay a = MonthDay.of(Month.MARCH, 15);
        MonthDay b = MonthDay.of(3, 15);
        check(a.equals(b), "of(Month,int) and of(int,int) should produce equal results");
    }

    static void expectDateTimeException(Month month, int day) {
        try {
            MonthDay.of(month, day);
            throw new RuntimeException("expected DateTimeException for " + month + " day " + day);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
