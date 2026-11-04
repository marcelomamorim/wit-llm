/*
 * @test
 * @summary Test java.time.MonthDay.get(TemporalField) method
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testGetValidFields();
        testGetInvalidField();
    }

    private static void testGetValidFields() {
        MonthDay md = MonthDay.of(Month.JANUARY, 15);
        // Test get with MONTH_OF_YEAR
        int month = md.get(ChronoField.MONTH_OF_YEAR);
        if (month != 1) {
            throw new AssertionError("Expected month 1 but got " + month);
        }
        // Test get with DAY_OF_MONTH
        int day = md.get(ChronoField.DAY_OF_MONTH);
        if (day != 15) {
            throw new AssertionError("Expected day 15 but got " + day);
        }
    }

    private static void testGetInvalidField() {
        MonthDay md = MonthDay.of(Month.FEBRUARY, 28);
        TemporalField invalidField = ChronoField.YEAR; // YEAR is not supported by MonthDay
        try {
            md.get(invalidField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for field: " + invalidField);
        } catch (UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }
}
