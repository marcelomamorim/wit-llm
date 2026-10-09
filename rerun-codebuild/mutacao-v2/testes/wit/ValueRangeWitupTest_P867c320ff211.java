/*
 * @test
 * @summary Test ValueRange.checkValidValue(long, TemporalField) for valid and invalid values
 * @run main ValueRangeWitupTest_P867c320ff211
 */

import java.time.DateTimeException;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;

public class ValueRangeWitupTest_P867c320ff211 {

    // Dummy TemporalField implementation for testing
    static class DummyField implements TemporalField {
        public boolean isDateBased() { return false; }
        public boolean isTimeBased() { return false; }
        public java.time.temporal.ValueRange range() { return null; }
        public long getFrom(java.time.temporal.TemporalAccessor temporal) { throw new UnsupportedOperationException(); }
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) { throw new UnsupportedOperationException(); }
        public java.time.temporal.TemporalUnit getBaseUnit() { return null; }
        public java.time.temporal.TemporalUnit getRangeUnit() { return null; }
        public String getDisplayName(java.util.Locale locale) { return "DummyField"; }
        public java.util.Map<java.time.temporal.TemporalUnit, java.time.temporal.ValueRange> rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) { return null; }
    }

    public static void main(String[] args) {
        testValidValueWithinRange();
        testInvalidValueBelowRange();
        testInvalidValueAboveRange();
    }

    private static void testValidValueWithinRange() {
        // Create a ValueRange with min=1, max=10
        ValueRange range = ValueRange.of(1, 10);
        DummyField field = new DummyField();
        long testValue = 5L;
        long result = range.checkValidValue(testValue, field);
        if (result != testValue) {
            throw new AssertionError("checkValidValue returned " + result + " but expected " + testValue);
        }
    }

    private static void testInvalidValueBelowRange() {
        ValueRange range = ValueRange.of(1, 10);
        DummyField field = new DummyField();
        long invalidValue = 0L; // less than min
        try {
            range.checkValidValue(invalidValue, field);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    private static void testInvalidValueAboveRange() {
        ValueRange range = ValueRange.of(1, 10);
        DummyField field = new DummyField();
        long invalidValue = 11L; // greater than max
        try {
            range.checkValidValue(invalidValue, field);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
