/*
 * @test
 * @summary Test java.time.MonthDay.getMonth() method
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testGetMonthReturnsCorrectMonth();
    }

    private static void testGetMonthReturnsCorrectMonth() {
        // Create MonthDay for January 15
        MonthDay md = MonthDay.of(1, 15);
        Month m = md.getMonth();
        if (m != Month.JANUARY) {
            throw new AssertionError("Expected JANUARY but got " + m);
        }

        // Create MonthDay for December 31
        md = MonthDay.of(12, 31);
        m = md.getMonth();
        if (m != Month.DECEMBER) {
            throw new AssertionError("Expected DECEMBER but got " + m);
        }
    }
}
