/*
 * @test
 * @summary Test ChronoField.checkValidIntValue(long) for valid and invalid values
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

public class ChronoFieldWitupTest {
    public static void main(String[] args) {
        ChronoField field = ChronoField.DAY_OF_MONTH;

        // Test valid value within range
        int val = field.checkValidIntValue(15L);
        if (val != 15) {
            throw new AssertionError("Expected 15 but got " + val);
        }

        // Test boundary values
        ValueRange range = field.range();
        int min = (int) range.getMinimum();
        int max = (int) range.getMaximum();

        int valMin = field.checkValidIntValue(min);
        if (valMin != min) {
            throw new AssertionError("Expected min " + min + " but got " + valMin);
        }

        int valMax = field.checkValidIntValue(max);
        if (valMax != max) {
            throw new AssertionError("Expected max " + max + " but got " + valMax);
        }

        // Test value below minimum - expect exception
        try {
            field.checkValidIntValue(min - 1L);
            throw new AssertionError("Expected DateTimeException for value below minimum");
        } catch (java.time.DateTimeException e) {
            // expected
        }

        // Test value above maximum - expect exception
        try {
            field.checkValidIntValue(max + 1L);
            throw new AssertionError("Expected DateTimeException for value above maximum");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
