/*
 * @test
 * @summary Test java.time.temporal.ValueRange.checkValidValue(long, TemporalField)
 * @run main ValueRangeWitupTest_P867c320ff211
 */

import java.time.DateTimeException;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;

public class ValueRangeWitupTest_P867c320ff211 {

    static class DummyField implements TemporalField {
        public String getName() { return "DummyField"; }
        public boolean isDateBased() { return false; }
        public boolean isTimeBased() { return false; }
        public ValueRange range() { return ValueRange.of(1, 10); }
        public long getFrom(java.time.temporal.TemporalAccessor temporal) { throw new UnsupportedOperationException(); }
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) { throw new UnsupportedOperationException(); }
        public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) { return range(); }
    }

    public static void main(String[] args) {
        testCheckValidValueValid();
        testCheckValidValueInvalid();
    }

    private static void testCheckValidValueValid() {
        ValueRange range = ValueRange.of(1, 10);
        DummyField field = new DummyField();
        // Test valid values within range
        for (long v = 1; v <= 10; v++) {
            long result = range.checkValidValue(v, field);
            if (result != v) {
                throw new AssertionError("checkValidValue returned " + result + " but expected " + v);
            }
        }
    }

    private static void testCheckValidValueInvalid() {
        ValueRange range = ValueRange.of(1, 10);
        DummyField field = new DummyField();
        // Test invalid values below minimum
        try {
            range.checkValidValue(0, field);
            throw new AssertionError("Expected DateTimeException for value 0");
        } catch (DateTimeException e) {
            // expected
        }
        // Test invalid values above maximum
        try {
            range.checkValidValue(11, field);
            throw new AssertionError("Expected DateTimeException for value 11");
        } catch (DateTimeException e) {
            // expected
        }
        // Test invalid negative value
        try {
            range.checkValidValue(-5, field);
            throw new AssertionError("Expected DateTimeException for value -5");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
