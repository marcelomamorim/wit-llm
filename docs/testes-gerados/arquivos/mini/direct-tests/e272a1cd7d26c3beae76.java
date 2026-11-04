/*
 * @test
 * @summary Test java.time.temporal.ValueRange.checkValidIntValue(long, TemporalField) method
 * @run main ValueRangeWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;

public class ValueRangeWitupTest {

    static class DummyField implements TemporalField {
        public String getName() { return "DummyField"; }
        public boolean isDateBased() { return false; }
        public boolean isTimeBased() { return false; }
        public ValueRange range() { return null; }
        public long getFrom(java.time.temporal.TemporalAccessor temporal) { return 0; }
        public <R extends java.time.temporal.Temporal> R adjustInto(R temporal, long newValue) { return temporal; }
        public ValueRange rangeRefinedBy(java.time.temporal.TemporalAccessor temporal) { return null; }
    }

    public static void main(String[] args) {
        ValueRange range = ValueRange.of(0, 10);
        TemporalField field = new DummyField();

        // Test valid int value within range
        int result = range.checkValidIntValue(5L, field);
        if (result != 5) {
            throw new AssertionError("Expected 5 but got " + result);
        }

        // Test boundary values
        if (range.checkValidIntValue(0L, field) != 0) {
            throw new AssertionError("Expected 0 at lower bound");
        }
        if (range.checkValidIntValue(10L, field) != 10) {
            throw new AssertionError("Expected 10 at upper bound");
        }

        // Test value below range - expect DateTimeException
        try {
            range.checkValidIntValue(-1L, field);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // expected
        }

        // Test value above range - expect DateTimeException
        try {
            range.checkValidIntValue(11L, field);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // expected
        }

        // Test value within int range but outside ValueRange
        ValueRange largeRange = ValueRange.of(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int val = largeRange.checkValidIntValue(123456789L, field);
        if (val != 123456789) {
            throw new AssertionError("Expected 123456789 but got " + val);
        }

        // Test value outside int range - expect DateTimeException
        try {
            largeRange.checkValidIntValue((long)Integer.MAX_VALUE + 1, field);
            throw new AssertionError("Expected DateTimeException for value outside int range");
        } catch (DateTimeException e) {
            // expected
        }

        System.out.println("All tests passed.");
    }
}
