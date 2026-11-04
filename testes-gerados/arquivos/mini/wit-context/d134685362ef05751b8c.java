/*
 * @test
 * @summary Test java.time.MonthDay.with(Month) method for null and normal behavior
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testWithNullMonth();
        testWithSameMonthReturnsThis();
        testWithDifferentMonthAdjustsDay();
    }

    private static void testWithNullMonth() {
        MonthDay md = MonthDay.of(1, 15);
        try {
            md.with(null);
            throw new AssertionError("Expected NullPointerException for null month");
        } catch (NullPointerException npe) {
            // expected
            if (!"month".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testWithSameMonthReturnsThis() {
        MonthDay md = MonthDay.of(Month.JANUARY, 10);
        MonthDay result = md.with(Month.JANUARY);
        if (result != md) {
            throw new AssertionError("Expected same instance when month is unchanged");
        }
    }

    private static void testWithDifferentMonthAdjustsDay() {
        MonthDay md = MonthDay.of(Month.JANUARY, 31);
        // February max length is 29 (leap year considered max)
        MonthDay febMd = md.with(Month.FEBRUARY);
        if (febMd.getMonth() != Month.FEBRUARY) {
            throw new AssertionError("Month should be February");
        }
        if (febMd.getDayOfMonth() != 29) {
            throw new AssertionError("Day should be adjusted to February max length 29");
        }
    }
}