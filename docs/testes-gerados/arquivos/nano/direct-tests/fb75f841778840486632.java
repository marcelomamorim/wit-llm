/*
 * @test
 * @summary Test Year.get(TemporalField) method for valid and invalid fields
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.TemporalField;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class YearWitupTest {

    public static void main(String[] args) {
        testGetWithSupportedField();
        testGetWithUnsupportedField();
    }

    static void testGetWithSupportedField() {
        Year year = Year.of(2023);
        try {
            int value = year.get(ChronoField.YEAR);
            assert value == 2023 : "Expected 2023, got " + value;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for supported field", e);
        }
    }

    static void testGetWithUnsupportedField() {
        Year year = Year.of(2023);
        TemporalField unsupportedField = new TemporalField() {
            @Override
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return false;
            }
            @Override
            public ValueRange range() {
                return null;
            }
            @Override
            public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
                return null;
            }
            @Override
            public Class<?> getBaseClass() {
                return null;
            }
            @Override
            public String getDisplayName() {
                return "unsupported";
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
            public boolean isSupportedBy(TemporalAccessor temporal) {
                return false;
            }
            @Override
            public <R extends java.lang.Object> R getFrom(TemporalAccessor temporal) {
                throw new UnsupportedTemporalTypeException("unsupported field");
            }
            @Override
            public <R extends java.lang.Object> R adjustInto(R temporal, long newValue) {
                return null;
            }
        };
        try {
            year.get(unsupportedField);
            throw new AssertionError("Expected UnsupportedTemporalTypeException for unsupported field");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
