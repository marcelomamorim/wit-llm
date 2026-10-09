/*
 * @test
 * @summary Test the static method JapaneseEra.of(int) for valid and invalid inputs
 * @run main JapaneseEraWitupTest_P8c5b01056259
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest_P8c5b01056259 {

    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }

    static void testValidEras() {
        try {
            // Known valid eras, assuming 1, 2, 3 are valid for the test context
            // Actual valid values depend on the implementation, but for testing, we assume 1, 2, 3
            JapaneseEra era1 = JapaneseEra.of(1);
            JapaneseEra era2 = JapaneseEra.of(2);
            JapaneseEra era3 = JapaneseEra.of(3);
            if (era1 == null || era2 == null || era3 == null) {
                throw new AssertionError("Valid era returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid eras: " + e);
        }
    }

    static void testInvalidEras() {
        int[] invalidEras = {0, -1, 1000};
        for (int invalid : invalidEras) {
            try {
                JapaneseEra.of(invalid);
                throw new AssertionError("Expected exception not thrown for invalid era: " + invalid);
            } catch (DateTimeException e) {
                // Expected exception
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for invalid era: " + e);
            }
        }
    }
}
