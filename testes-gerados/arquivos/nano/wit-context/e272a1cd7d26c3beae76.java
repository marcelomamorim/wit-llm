/*
 * @test
 * @summary Test for ValueRange.checkValidIntValue method
 * @run main ValueRangeWitupTest
 */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testCheckValidIntValueWithinRange();
        testCheckValidIntValueBelowRange();
        testCheckValidIntValueAboveRange();
    }

    static void testCheckValidIntValueWithinRange() {
        // Setup: create a ValueRange that includes the value 10
        ValueRange range = ValueRange.of(5, 15);
        // Mock a TemporalField (not used in logic, so null is acceptable)
        TemporalField field = null;
        try {
            int result = range.checkValidIntValue(10, field);
            if (result != 10) {
                throw new AssertionError("Expected 10, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value", e);
        }
    }

    static void testCheckValidIntValueBelowRange() {
        ValueRange range = ValueRange.of(5, 15);
        TemporalField field = null;
        try {
            range.checkValidIntValue(4, field);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    static void testCheckValidIntValueAboveRange() {
        ValueRange range = ValueRange.of(5, 15);
        TemporalField field = null;
        try {
            range.checkValidIntValue(16, field);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
