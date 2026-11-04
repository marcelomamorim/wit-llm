/*
 * @test
 * @summary Test for MonthDay.getMonth() method.
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testGetMonth();
    }

    public static void testGetMonth() {
        MonthDay md = MonthDay.of(1, 15); // January 15
        Month month = md.getMonth();
        assert month == Month.JANUARY : "Expected Month.JANUARY, but got " + month;

        md = MonthDay.of(12, 31); // December 31
        month = md.getMonth();
        assert month == Month.DECEMBER : "Expected Month.DECEMBER, but got " + month;

        md = MonthDay.of(6, 10); // June 10
        month = md.getMonth();
        assert month == Month.JUNE : "Expected Month.JUNE, but got " + month;
    }
}
