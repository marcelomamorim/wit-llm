/* @test    @summary Test for checkValidIntValue method in java.time.temporal.ValueRange    @run main ValueRangeWitupTest_P08d567b7f76c */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P08d567b7f76c {

    public static void main(String[] args) {
        testCheckValidIntValueWithinRange();
        testCheckValidIntValueBelowRange();
        testCheckValidIntValueAboveRange();
        System.out.println("All tests passed.");
    }

    private static void testCheckValidIntValueWithinRange() {
        // Setup: create a ValueRange that includes the value 10
        ValueRange range = ValueRange.of(5, 15);
        // Use a dummy TemporalField (null) as the field parameter
        TemporalField field = null;
        try {
            int result = range.checkValidIntValue(10, field);
            if (result != 10) {
                throw new AssertionError("Expected 10, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value: " + e);
        }
    }

    private static void testCheckValidIntValueBelowRange() {
        // Setup: create a ValueRange that does not include 4
        ValueRange range = ValueRange.of(5, 15);
        TemporalField field = null;
        try {
            range.checkValidIntValue(4, field);
            throw new AssertionError("Expected DateTimeException for value below range");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testCheckValidIntValueAboveRange() {
        // Setup: create a ValueRange that does not include 20
        ValueRange range = ValueRange.of(5, 15);
        TemporalField field = null;
        try {
            range.checkValidIntValue(20, field);
            throw new AssertionError("Expected DateTimeException for value above range");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
