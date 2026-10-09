/*
 * @test
 * @summary Test ValueRange.checkValidIntValue(long, TemporalField) for valid and invalid inputs
 * @run main ValueRangeWitupTest_P220c9d2c8819
 */

import java.time.DateTimeException;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;

public class ValueRangeWitupTest_P220c9d2c8819 {

    // Dummy TemporalField implementation for testing
    static class DummyField implements TemporalField {
        public String getName() { return "DummyField"; }
        public boolean isDateBased() { return false; }
        public boolean isTimeBased() { return false; }
        public ValueRange range() { return null; }
        public boolean isSupportedBy(java.time.temporal.TemporalAccessor temporal) { return false; }
        public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) { return null; }
        public long getFrom(java.time.temporal.TemporalAccessor temporal) { return 0; }
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) { return temporal; }
    }

    public static void main(String[] args) {
        DummyField field = new DummyField();

        // Create a ValueRange with int-compatible bounds
        ValueRange range = ValueRange.of(1, 10);

        // Test valid values within range
        for (long v = 1; v <= 10; v++) {
            int result = range.checkValidIntValue(v, field);
            if (result != (int) v) {
                throw new AssertionError("checkValidIntValue returned wrong int for value " + v);
            }
        }

        // Test values outside the range - expect DateTimeException
        long[] invalidValues = {0, 11, Long.MIN_VALUE, Long.MAX_VALUE};
        for (long invalid : invalidValues) {
            boolean thrown = false;
            try {
                range.checkValidIntValue(invalid, field);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for value " + invalid);
            }
        }

        // Test a ValueRange with minSmallest and maxLargest within int range but value outside
        ValueRange complexRange = ValueRange.of(1, 5, 10);
        // valid values
        int valid = complexRange.checkValidIntValue(5, field);
        if (valid != 5) {
            throw new AssertionError("checkValidIntValue returned wrong int for value 5");
        }
        // invalid values
        long[] invalids = {0, 11};
        for (long invalid : invalids) {
            boolean thrown = false;
            try {
                complexRange.checkValidIntValue(invalid, field);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for value " + invalid);
            }
        }

        System.out.println("All tests passed.");
    }
}
