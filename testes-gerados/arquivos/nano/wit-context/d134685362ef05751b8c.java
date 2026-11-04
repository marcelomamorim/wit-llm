/*
 * @test
 * @summary Tests for java.time.MonthDay class, focusing on the 'with' method.
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testWithNullMonth();
        testWithSameMonth();
        testWithDifferentMonth();
    }

    private static void testWithNullMonth() {
        MonthDay md = MonthDay.of(5, 15);
        try {
            md.with(null);
            System.out.println("FAIL: Expected NullPointerException for null month");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    private static void testWithSameMonth() {
        MonthDay md = MonthDay.of(7, 20);
        MonthDay result = md.with(Month.JULY);
        if (result == md) {
            System.out.println("PASS: with() returned same instance for same month");
        } else {
            System.out.println("FAIL: with() did not return same instance for same month");
        }
    }

    private static void testWithDifferentMonth() {
        MonthDay md = MonthDay.of(12, 31);
        MonthDay result = md.with(Month.JANUARY);
        if (result.getMonth() == Month.JANUARY && result.getDayOfMonth() == Math.min(31, Month.JANUARY.maxLength())) {
            System.out.println("PASS: with() returned correct MonthDay for different month");
        } else {
            System.out.println("FAIL: with() returned incorrect MonthDay for different month");
        }
    }
}
