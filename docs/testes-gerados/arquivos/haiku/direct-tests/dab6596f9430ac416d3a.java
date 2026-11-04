/*
 * @test
 * @summary Verify MonthDay.get(TemporalField) returns field values and rejects unsupported fields
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        MonthDay leapDay = MonthDay.of(2, 29);
        check(leapDay.get(ChronoField.MONTH_OF_YEAR) == 2, "MONTH_OF_YEAR of Feb 29 should be 2");
        check(leapDay.get(ChronoField.DAY_OF_MONTH) == 29, "DAY_OF_MONTH of Feb 29 should be 29");

        MonthDay yearEnd = MonthDay.of(12, 31);
        check(yearEnd.get(ChronoField.MONTH_OF_YEAR) == 12, "MONTH_OF_YEAR of Dec 31 should be 12");
        check(yearEnd.get(ChronoField.DAY_OF_MONTH) == 31, "DAY_OF_MONTH of Dec 31 should be 31");

        MonthDay firstDay = MonthDay.of(1, 1);
        check(firstDay.get(ChronoField.MONTH_OF_YEAR) == 1, "MONTH_OF_YEAR of Jan 1 should be 1");
        check(firstDay.get(ChronoField.DAY_OF_MONTH) == 1, "DAY_OF_MONTH of Jan 1 should be 1");

        boolean thrown = false;
        try {
            leapDay.get(ChronoField.YEAR);
        } catch (UnsupportedTemporalTypeException e) {
            thrown = true;
        }
        check(thrown, "get(YEAR) should throw UnsupportedTemporalTypeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
