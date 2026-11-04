/* @test    @summary Test checkValidIntValue with valid and invalid inputs    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testCheckValidIntValueValid();
        testCheckValidIntValueInvalid();
    }

    static void testCheckValidIntValueValid() {
        // Create a dummy ValueRange with min=0, max=10
        ValueRange range = ValueRange.of(0, 10);
        // Use a valid value within the range
        long validValue = 5;
        // Create a dummy TemporalField (null, as not used in logic)
        TemporalField field = null;
        try {
            int result = range.checkValidIntValue(validValue, field);
            if (result != validValue) {
                throw new AssertionError("Expected " + validValue + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value", e);
        }
    }

    static void testCheckValidIntValueInvalid() {
        // Create a dummy ValueRange with min=0, max=10
        ValueRange range = ValueRange.of(0, 10);
        // Use an invalid value outside the range
        long invalidValue = 20;
        // Create a dummy TemporalField (null, as not used in logic)
        TemporalField field = null;
        try {
            range.checkValidIntValue(invalidValue, field);
            throw new AssertionError("Expected DateTimeException for invalid value");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
