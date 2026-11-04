/*
 * @test
 * @summary Test java.time.temporal.ChronoField.checkValidValue(long) for valid and invalid values
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.time.temporal.DateTimeException;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        ChronoField field = ChronoField.DAY_OF_MONTH;

        // Test with a valid value within the range
        long validValue = 15L;
        long result = field.checkValidValue(validValue);
        if (result != validValue) {
            throw new AssertionError("checkValidValue returned " + result + " but expected " + validValue);
        }

        // Test with a value smaller than the minimum (should throw DateTimeException)
        long tooSmall = field.range().getMinimum() - 1;
        try {
            field.checkValidValue(tooSmall);
            throw new AssertionError("Expected DateTimeException for value " + tooSmall);
        } catch (DateTimeException expected) {
            // Expected exception
        }

        // Test with a value larger than the maximum (should throw DateTimeException)
        long tooLarge = field.range().getMaximum() + 1;
        try {
            field.checkValidValue(tooLarge);
            throw new AssertionError("Expected DateTimeException for value " + tooLarge);
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
