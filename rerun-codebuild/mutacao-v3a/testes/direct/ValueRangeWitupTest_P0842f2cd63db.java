/* @test    @summary Test checkValidValue method for valid and invalid values    @run main ValueRangeWitupTest_P0842f2cd63db */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest_P0842f2cd63db {
    public static void main(String[] args) {
        testCheckValidValueValid();
        testCheckValidValueInvalid();
    }

    static void testCheckValidValueValid() {
        ValueRange range = ValueRange.of(1, 31);
        long value = 15;
        try {
            long result = range.checkValidValue(value, null);
            if (result != value) {
                throw new AssertionError("Expected " + value + " but got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value: " + e);
        }
    }

    static void testCheckValidValueInvalid() {
        ValueRange range = ValueRange.of(1, 31);
        long invalidValue = 32;
        try {
            range.checkValidValue(invalidValue, null);
            throw new AssertionError("Expected DateTimeException for invalid value")
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
