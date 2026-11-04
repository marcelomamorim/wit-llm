/*
 * @test
 * @summary Deterministic tests for java.time.YearMonth.get(TemporalField)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testGetWithValidField();
        testGetWithInvalidField();
    }

    private static void testGetWithValidField() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        TemporalField field = ChronoField.MONTH_OF_YEAR;
        int value = ym.get(field);
        if (value != 1) {
            throw new AssertionError("Expected month 1 but got " + value);
        }

        field = ChronoField.YEAR;
        value = ym.get(field);
        if (value != 2020) {
            throw new AssertionError("Expected year 2020 but got " + value);
        }
    }

    private static void testGetWithInvalidField() {
        YearMonth ym = YearMonth.of(2020, Month.JANUARY);
        TemporalField invalidField = new TemporalField() {
            @Override
            public boolean isSupportedBy(java.time.temporal.TemporalAccessor temporal) {
                return false;
            }

            @Override
            public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) {
                return ValueRange.of(1, 10);
            }

            @Override
            public long getFrom(java.time.temporal.TemporalAccessor temporal) {
                throw new UnsupportedOperationException("getFrom not supported");
            }

            @Override
            public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
                throw new UnsupportedOperationException("adjustInto not supported");
            }

            @Override
            public String getName() {
                return "InvalidField";
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return ChronoField.DAYS.getBaseUnit();
            }

            @Override
            public TemporalUnit getRangeUnit() {
                return ChronoField.DAYS.getRangeUnit();
            }

            @Override
            public boolean isDateBased() {
                return true;
            }

            @Override
            public boolean isTimeBased() {
                return false;
            }
        };

        try {
            ym.get(invalidField);
            throw new AssertionError("Expected UnsupportedOperationException or IllegalArgumentException for invalid field");
        } catch (UnsupportedOperationException | IllegalArgumentException e) {
            // Expected exception
        }
    }
}
