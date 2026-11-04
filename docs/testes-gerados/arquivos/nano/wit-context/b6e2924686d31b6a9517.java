/*
 * @test
 * @summary Tests for java.time.chrono.JapaneseEra.of(int)
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEraBelowRange();
        testInvalidEraAboveRange();
    }

    static void testValidEra() {
        try {
            // Test with a known valid era value, e.g., 1
            JapaneseEra era = JapaneseEra.of(1);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era: " + e);
        }
    }

    static void testInvalidEraBelowRange() {
        try {
            // Test with a value below the valid range, e.g., -2
            JapaneseEra.of(-2);
            throw new AssertionError("Expected DateTimeException for era -2");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for era -2: " + e);
        }
    }

    static void testInvalidEraAboveRange() {
        try {
            // Test with a value above the valid range, e.g., 100
            JapaneseEra.of(100);
            throw new AssertionError("Expected DateTimeException for era 100");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for era 100: " + e);
        }
    }
}
