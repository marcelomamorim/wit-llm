/* @test    @summary Test checkValidIntValue with valid and invalid values    @run main ValueRangeWitupTest_P31baa8e11115 */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P31baa8e11115 {

    public static void main(String[] args) {
        testCheckValidIntValueValid();
        testCheckValidIntValueInvalid();
    }

    static void testCheckValidIntValueValid() {
        try {
            // Assuming a typical range, e.g., for day-of-month 1..31
            ValueRange range = ValueRange.of(1, 31);
            // 'value' within the range
            int result = range.checkValidIntValue(15, new DummyTemporalField());
            assert result == 15 : "Expected 15, got " + result;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value", e);
        }
    }

    static void testCheckValidIntValueInvalid() {
        try {
            // Assuming a typical range, e.g., for day-of-month 1..31
            ValueRange range = ValueRange.of(1, 31);
            // 'value' outside the range
            range.checkValidIntValue(40, new DummyTemporalField());
            throw new AssertionError("Expected DateTimeException for invalid value");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static class DummyTemporalField implements java.time.temporal.TemporalField {
        @Override
        public String toString() { return "DummyField"; }
        // Other methods are not used in this test
    }
}
