/*
 * @test
 * @summary Deterministic tests for java.time.Year.get(TemporalField)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testGetWithSupportedFields();
        testGetWithUnsupportedField();
        testGetWithYearOfEraField();
    }

    private static void testGetWithSupportedFields() {
        Year y = Year.of(2020);
        // YEAR field should return the year value
        int yearValue = y.get(ChronoField.YEAR);
        if (yearValue != 2020) {
            throw new AssertionError("Expected 2020 for YEAR field, got " + yearValue);
        }
        // ERA field should return 1 for AD
        int eraValue = y.get(ChronoField.ERA);
        if (eraValue != 1) {
            throw new AssertionError("Expected 1 for ERA field, got " + eraValue);
        }
    }

    private static void testGetWithUnsupportedField() {
        Year y = Year.of(2020);
        TemporalField unsupportedField = new TemporalField() {
            @Override
            public boolean isSupportedBy(java.time.temporal.TemporalAccessor temporal) {
                return false;
            }

            @Override
            public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) {
                return ValueRange.of(0, 10);
            }

            @Override
            public long getFrom(java.time.temporal.TemporalAccessor temporal) {
                throw new UnsupportedTemporalTypeException("Unsupported field");
            }

            @Override
            public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) {
                throw new UnsupportedTemporalTypeException("Unsupported field");
            }

            @Override
            public String getName() {
                return "UnsupportedField";
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
            y.get(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported field");
        } catch (UnsupportedTemporalTypeException e) {
            // Expected
        }
    }

    private static void testGetWithYearOfEraField() {
        Year y = Year.of(1); // Year 1 AD
        int yearOfEra = y.get(ChronoField.YEAR_OF_ERA);
        if (yearOfEra != 1) {
            throw new AssertionError("Expected 1 for YEAR_OF_ERA field, got " + yearOfEra);
        }

        Year yBeforeEra = Year.of(0); // Year 0 is 1 BCE
        int yearOfEraBefore = yBeforeEra.get(ChronoField.YEAR_OF_ERA);
        if (yearOfEraBefore != 1) {
            throw new AssertionError("Expected 1 for YEAR_OF_ERA field for year 0 (1 BCE), got " + yearOfEraBefore);
        }
    }
}
