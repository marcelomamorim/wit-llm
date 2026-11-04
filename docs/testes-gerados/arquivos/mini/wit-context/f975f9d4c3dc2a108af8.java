/*
 * @test
 * @summary Test java.time.temporal.ChronoField.checkValidIntValue(long) for valid and invalid inputs
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.time.temporal.DateTimeException;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        ChronoField field = ChronoField.DAY_OF_MONTH;

        // Test with a valid int value within the range
        int validValue = 15;
        int result = field.checkValidIntValue(validValue);
        if (result != validValue) {
            throw new AssertionError("Expected " + validValue + " but got " + result);
        }

        // Test with a value equal to min of range
        int minValue = (int) field.range().getMinimum();
        result = field.checkValidIntValue(minValue);
        if (result != minValue) {
            throw new AssertionError("Expected min " + minValue + " but got " + result);
        }

        // Test with a value equal to max of range
        int maxValue = (int) field.range().getMaximum();
        result = field.checkValidIntValue(maxValue);
        if (result != maxValue) {
            throw new AssertionError("Expected max " + maxValue + " but got " + result);
        }

        // Test with a value below the minimum - expect DateTimeException
        long belowMin = field.range().getMinimum() - 1;
        try {
            field.checkValidIntValue(belowMin);
            throw new AssertionError("Expected DateTimeException for value below minimum");
        } catch (DateTimeException expected) {
            // Expected exception
        }

        // Test with a value above the maximum - expect DateTimeException
        long aboveMax = field.range().getMaximum() + 1;
        try {
            field.checkValidIntValue(aboveMax);
            throw new AssertionError("Expected DateTimeException for value above maximum");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
