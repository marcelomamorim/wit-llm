/*
 * @test
 * @summary Test ChronoField.checkValidValue() for various inputs.
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testCheckValidValueWithinRange();
        testCheckValidValueBelowRange();
        testCheckValidValueAboveRange();
    }

    static void testCheckValidValueWithinRange() {
        long validValue = 1L;
        long result = ChronoField.checkValidValue(validValue);
        if (result != validValue) {
            throw new AssertionError("Expected " + validValue + " but got " + result);
        }
    }

    static void testCheckValidValueBelowRange() {
        long invalidValue = -1L;
        try {
            ChronoField.checkValidValue(invalidValue);
            throw new AssertionError("Expected exception for value below range");
        } catch (RuntimeException e) {
            // Expected exception
        }
    }

    static void testCheckValidValueAboveRange() {
        long invalidValue = Long.MAX_VALUE;
        try {
            ChronoField.checkValidValue(invalidValue);
            throw new AssertionError("Expected exception for value above range");
        } catch (RuntimeException e) {
            // Expected exception
        }
    }
}
