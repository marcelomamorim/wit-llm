/*
 * @test
 * @summary Witup-derived behavioral checks for MonthDay.get(TemporalField)
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        check(MonthDay.of(Month.MARCH, 15).get(ChronoField.MONTH_OF_YEAR) == 3, "month of year");
        check(MonthDay.of(Month.MARCH, 15).get(ChronoField.DAY_OF_MONTH) == 15, "day of month");
        check(MonthDay.of(12, 31).get(ChronoField.MONTH_OF_YEAR) == 12, "december month");
        check(MonthDay.of(12, 31).get(ChronoField.DAY_OF_MONTH) == 31, "december last day");
        check(MonthDay.of(2, 29).get(ChronoField.DAY_OF_MONTH) == 29, "leap day accepted");
        check(MonthDay.of(2, 28).get(ChronoField.DAY_OF_MONTH) == 28, "february 28");
        check(MonthDay.of(1, 1).get(ChronoField.DAY_OF_MONTH) == 1, "first day");

        expectUnsupported(MonthDay.of(3, 15), ChronoField.HOUR_OF_DAY);
        expectUnsupported(MonthDay.of(3, 15), ChronoField.YEAR);
    }

    static void expectUnsupported(MonthDay md, ChronoField field) {
        try {
            md.get(field);
        } catch (UnsupportedTemporalTypeException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedTemporalTypeException for " + field);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
