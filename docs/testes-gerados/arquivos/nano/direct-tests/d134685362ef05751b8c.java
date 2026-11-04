/*
 * @test
 * @summary Test the with(Month) method of MonthDay for various scenarios
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testWithSameMonth();
        testWithDifferentMonth();
        testWithNullMonth();
        testWithEdgeMonths();
    }

    static void testWithSameMonth() {
        MonthDay md = MonthDay.of(5, 15);
        MonthDay result = md.with(Month.MAY);
        assert result.equals(md) : "Expected same MonthDay instance when using same month";
    }

    static void testWithDifferentMonth() {
        MonthDay md = MonthDay.of(5, 15);
        MonthDay result = md.with(Month.JUNE);
        assert result.getMonth() == Month.JUNE : "Month should be June";
        int expectedDay = Math.min(15, Month.JUNE.maxLength());
        assert result.getDayOfMonth() == expectedDay : "Day of month should be min of original day and max length of new month";
    }

    static void testWithNullMonth() {
        try {
            MonthDay md = MonthDay.of(5, 15);
            md.with(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testWithEdgeMonths() {
        MonthDay md = MonthDay.of(2, 29);
        MonthDay result = md.with(Month.FEBRUARY);
        int expectedDay = Math.min(29, Month.FEBRUARY.maxLength());
        assert result.getMonth() == Month.FEBRUARY : "Month should be February";
        assert result.getDayOfMonth() == expectedDay : "Day of month should be min of original day and max length of new month";
    }
}
