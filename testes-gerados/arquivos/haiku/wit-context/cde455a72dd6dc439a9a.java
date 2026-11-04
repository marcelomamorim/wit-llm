/*
 * @test
 * @summary Witup-derived tests for MonthDay.of(Month, int)
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testNullMonthThrowsNPE();
        testNullMonthCheckedBeforeDay();
        testInvalidDayLowThrowsDateTimeException();
        testInvalidDayHighThrowsDateTimeException();
        testFebruary30ThrowsDateTimeException();
        testApril31ThrowsDateTimeException();
        testValidBoundaries();
    }

    static void testNullMonthThrowsNPE() {
        expectNPE(null, 1);
    }

    static void testNullMonthCheckedBeforeDay() {
        expectNPE(null, 0);
        expectNPE(null, 99);
    }

    static void testInvalidDayLowThrowsDateTimeException() {
        expectDateTimeException(Month.JANUARY, 0);
        expectDateTimeException(Month.JANUARY, -1);
    }

    static void testInvalidDayHighThrowsDateTimeException() {
        expectDateTimeException(Month.JANUARY, 32);
        expectDateTimeException(Month.DECEMBER, 32);
    }

    static void testFebruary30ThrowsDateTimeException() {
        expectDateTimeException(Month.FEBRUARY, 30);
        expectDateTimeException(Month.FEBRUARY, 31);
    }

    static void testApril31ThrowsDateTimeException() {
        expectDateTimeException(Month.APRIL, 31);
        expectDateTimeException(Month.JUNE, 31);
    }

    static void testValidBoundaries() {
        check(MonthDay.of(Month.FEBRUARY, 29), 2, 29);
        check(MonthDay.of(Month.JANUARY, 1), 1, 1);
        check(MonthDay.of(Month.DECEMBER, 31), 12, 31);
        check(MonthDay.of(Month.APRIL, 30), 4, 30);
    }

    static void check(MonthDay md, int expectedMonth, int expectedDay) {
        if (md.getMonthValue() != expectedMonth || md.getDayOfMonth() != expectedDay) {
            throw new RuntimeException("Unexpected MonthDay: " + md);
        }
    }

    static void expectNPE(Month month, int day) {
        try {
            MonthDay.of(month, day);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for day " + day);
    }

    static void expectDateTimeException(Month month, int day) {
        try {
            MonthDay.of(month, day);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException for " + month + " day " + day);
    }
}
