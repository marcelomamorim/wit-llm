/* @test    @summary Test checkValidValue method of ValueRange for valid and invalid values    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;
import java.time.temporal.TemporalField;
import java.time.DateTimeException;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testCheckValidValueWithValidInput();
        testCheckValidValueWithInvalidInput();
    }

    static void testCheckValidValueWithValidInput() {
        ValueRange range = ValueRange.of(1, 31);
        long validValue = 15;
        try {
            long result = range.checkValidValue(validValue, null);
            if (result != validValue) {
                throw new AssertionError("Expected value: " + validValue + ", but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid value: " + e);
        }
    }

    static void testCheckValidValueWithInvalidInput() {
        ValueRange range = ValueRange.of(1, 31);
        long invalidValue = 32;
        boolean exceptionThrown = false;
        try {
            range.checkValidValue(invalidValue, null);
        } catch (DateTimeException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected DateTimeException for invalid value")
        }
    }
}
