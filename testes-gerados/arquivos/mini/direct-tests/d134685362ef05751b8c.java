/*
 * @test
 * @summary Test java.time.MonthDay.with(Month) method behavior
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testWithSameMonthReturnsThis();
        testWithDifferentMonthAdjustsDay();
        testWithNullMonthThrowsNPE();
    }

    private static void testWithSameMonthReturnsThis() {
        MonthDay md = MonthDay.of(5, 15); // May 15
        MonthDay result = md.with(Month.MAY);
        if (result != md) {
            throw new AssertionError("Expected same instance when month is unchanged");
        }
        if (result.getMonthValue() != 5 || result.getDayOfMonth() != 15) {
            throw new AssertionError("MonthDay values changed unexpectedly");
        }
    }

    private static void testWithDifferentMonthAdjustsDay() {
        MonthDay md = MonthDay.of(1, 31); // January 31
        MonthDay result = md.with(Month.APRIL); // April max length 30
        if (result == md) {
            throw new AssertionError("Expected different instance when month changes");
        }
        if (result.getMonthValue() != 4) {
            throw new AssertionError("Expected month to be April (4)");
        }
        if (result.getDayOfMonth() != 30) {
            throw new AssertionError("Expected day to be adjusted to 30 for April");
        }
    }

    private static void testWithNullMonthThrowsNPE() {
        MonthDay md = MonthDay.of(6, 15);
        try {
            md.with(null);
            throw new AssertionError("Expected NullPointerException when month is null");
        } catch (NullPointerException npe) {
            if (!"month".equals(npe.getMessage())) {
                throw new AssertionError("Expected NPE message to be 'month'");
            }
        }
    }
}
