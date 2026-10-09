/* @test    @summary Test for ValueRange.checkValidIntValue(long, TemporalField) method behavior and exception paths    @run main ValueRangeWitupTest_P31baa8e11115 */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P31baa8e11115 {

    public static void main(String[] args) {
        testCheckValidIntValue_Valid();
        testCheckValidIntValue_Invalid();
    }

    static void testCheckValidIntValue_Valid() {
        // Test with a value within valid int range
        try {
            // Use a value that is valid (e.g., 100)
            long value = 100L;
            TemporalField field = null; // null is acceptable if method handles null; otherwise, create a mock or dummy
            // Since the method under test throws only if invalid, and the source code checks isValidIntValue, assume value is valid
            int result = ValueRange.of(1, 31).checkValidIntValue(value, field);
            // No exception expected
            if (result != value) {
                throw new AssertionError("Expected result to be equal to input value");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value: " + e);
        }
    }

    static void testCheckValidIntValue_Invalid() {
        // Test with a value that triggers DateTimeException
        try {
            long invalidValue = Long.MAX_VALUE; // Clearly outside typical int range
            TemporalField field = null; // null placeholder
            ValueRange.of(1, 31).checkValidIntValue(invalidValue, field);
            throw new AssertionError("Expected DateTimeException for invalid value");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
