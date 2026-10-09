/*
 * @test
 * @summary Tests for java.time.chrono.JapaneseEra.of(int)
 * @run main JapaneseEraWitupTest_P8ae551a4977b
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest_P8ae551a4977b {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEraNegative();
        testInvalidEraExceeding();
    }

    static void testValidEra() {
        try {
            // Test with a valid era value, e.g., 1, assuming it's within known eras
        
            JapaneseEra era = JapaneseEra.of(1);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era: " + e);
        }
    }

    static void testInvalidEraNegative() {
        try {
            JapaneseEra.of(-2);
            throw new AssertionError("Expected DateTimeException for negative era");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }

    static void testInvalidEraExceeding() {
        try {
            // Assuming KNOWN_ERAS.length is at least 1, test with a value exceeding bounds
            JapaneseEra.of(1000);
            throw new AssertionError("Expected DateTimeException for excessively high era");
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
