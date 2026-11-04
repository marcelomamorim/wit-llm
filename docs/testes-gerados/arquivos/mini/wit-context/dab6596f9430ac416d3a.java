/*
 * @test
 * @summary Deterministic tests for java.time.MonthDay.get(TemporalField)
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testGetWithValidFields();
        testGetWithUnsupportedField();
    }

    private static void testGetWithValidFields() {
        MonthDay md = MonthDay.of(Month.JANUARY, 15);
        // Test get with MONTH_OF_YEAR returns correct month value
        int month = md.get(ChronoField.MONTH_OF_YEAR);
        if (month != 1) {
            throw new AssertionError("Expected month 1 but got " + month);
        }
        // Test get with DAY_OF_MONTH returns correct day value
        int day = md.get(ChronoField.DAY_OF_MONTH);
        if (day != 15) {
            throw new AssertionError("Expected day 15 but got " + day);
        }
    }

    private static void testGetWithUnsupportedField() {
        MonthDay md = MonthDay.of(Month.JANUARY, 15);
        TemporalField unsupportedField = new TemporalField() {
            @Override
            public boolean isSupportedBy(java.time.temporal.TemporalAccessor temporal) {
                return false;
            }

            @Override
            public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) {
                return null;
            }

            @Override
            public boolean isDateBased() {
                return false;
            }

            @Override
            public boolean isTimeBased() {
                return false;
            }

            @Override
            public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
                throw new UnsupportedOperationException();
            }

            @Override
            public long getFrom(java.time.temporal.TemporalAccessor temporal) {
                throw new UnsupportedOperationException();
            }

            @Override
            public ValueRange range() {
                return ValueRange.of(1, 10);
            }

            @Override
            public String toString() {
                return "UnsupportedField";
            }
        };

        try {
            md.get(unsupportedField);
            throw new AssertionError("Expected IllegalArgumentException for unsupported field");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
