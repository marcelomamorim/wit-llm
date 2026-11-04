/*
 * @test
 * @summary Test for MonthDay.get(TemporalField)
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testGetDayOfMonth();
        testGetMonthOfYear();
        testGetUnsupportedField();
    }

    static void testGetDayOfMonth() {
        MonthDay md = MonthDay.of(5, 15);
        int day = md.get(ChronoField.DAY_OF_MONTH);
        assert day == 15 : "Expected 15, got " + day;
    }

    static void testGetMonthOfYear() {
        MonthDay md = MonthDay.of(12, 25);
        int month = md.get(ChronoField.MONTH_OF_YEAR);
        assert month == 12 : "Expected 12, got " + month;
    }

    static void testGetUnsupportedField() {
        MonthDay md = MonthDay.of(1, 1);
        try {
            md.get(ChronoField.YEAR);
            assert false : "Expected UnsupportedTemporalTypeException";
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
