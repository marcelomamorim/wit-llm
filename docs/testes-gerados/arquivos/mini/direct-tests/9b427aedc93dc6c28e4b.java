/*
 * @test
 * @summary Test ChronoField.checkValidValue(long) for valid and invalid values
 * @run main java.time.temporal.ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        ChronoField field = ChronoField.DAY_OF_MONTH;

        // Test valid value within range
        long validValue = 15L;
        long result = field.checkValidValue(validValue);
        if (result != validValue) {
            throw new AssertionError("Expected " + validValue + " but got " + result);
        }

        // Test boundary values
        ValueRange range = field.range();
        long min = range.getMinimum();
        long max = range.getMaximum();

        // Minimum valid value
        if (field.checkValidValue(min) != min) {
            throw new AssertionError("Minimum value check failed");
        }

        // Maximum valid value
        if (field.checkValidValue(max) != max) {
            throw new AssertionError("Maximum value check failed");
        }

        // Test invalid value below minimum
        try {
            field.checkValidValue(min - 1);
            throw new AssertionError("Expected exception for value below minimum");
        } catch (java.time.DateTimeException e) {
            // Expected
        }

        // Test invalid value above maximum
        try {
            field.checkValidValue(max + 1);
            throw new AssertionError("Expected exception for value above maximum");
        } catch (java.time.DateTimeException e) {
            // Expected
        }

        System.out.println("All tests passed.");
    }
}
