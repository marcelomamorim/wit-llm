/*
 * @test
 * @summary Test the static method JapaneseEra.of(int) for valid and invalid inputs
 * @run main JapaneseEraWitupTest_P1cbef482784a
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest_P1cbef482784a {

    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }

    static void testValidEras() {
        // Test known valid eras, assuming 1, 2, 3 are valid based on typical Japanese eras
        try {
            JapaneseEra era1 = JapaneseEra.of(1);
            System.out.println("Era 1: " + era1);
        } catch (Exception e) {
            System.err.println("Failed to get era 1: " + e);
        }
        try {
            JapaneseEra era2 = JapaneseEra.of(2);
            System.out.println("Era 2: " + era2);
        } catch (Exception e) {
            System.err.println("Failed to get era 2: " + e);
        }
        try {
            JapaneseEra era3 = JapaneseEra.of(3);
            System.out.println("Era 3: " + era3);
        } catch (Exception e) {
            System.err.println("Failed to get era 3: " + e);
        }
    }

    static void testInvalidEras() {
        // Test invalid eras, e.g., 0, -1, and a large number
        int[] invalidEras = {0, -1, 9999};
        for (int invalid : invalidEras) {
            try {
                JapaneseEra.of(invalid);
                System.err.println("Unexpected success for invalid era: " + invalid);
            } catch (DateTimeException e) {
                System.out.println("Correctly caught exception for era " + invalid + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected exception type for era " + invalid + ": " + e);
            }
        }
    }
}
