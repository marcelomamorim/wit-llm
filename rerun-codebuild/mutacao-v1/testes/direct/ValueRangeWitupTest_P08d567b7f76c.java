/* @test    @summary Test checkValidIntValue with valid and invalid inputs    @run main ValueRangeWitupTest_P08d567b7f76c */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P08d567b7f76c {

    public static void main(String[] args) {
        testCheckValidIntValueValid();
        testCheckValidIntValueInvalid();
    }

    static void testCheckValidIntValueValid() {
        try {
            // Simulate a ValueRange that accepts 0 to 10
            ValueRange range = ValueRange.of(0, 10);
            // Use a valid value within range
            int result = range.checkValidIntValue(5, new DummyField());
            if (result != 5) {
                throw new AssertionError("Expected 5, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value: " + e);
        }
    }

    static void testCheckValidIntValueInvalid() {
        try {
            // Simulate a ValueRange that accepts 0 to 10
            ValueRange range = ValueRange.of(0, 10);
            // Use an invalid value outside range
            range.checkValidIntValue(15, new DummyField());
            throw new AssertionError("Expected DateTimeException for invalid value");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static class DummyField implements TemporalField {
        @Override
        public String toString() { return "DummyField"; }
        // All other methods are not used in this test and can be left unimplemented or with dummy returns
    }
}
