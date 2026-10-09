/*
 * @test
 * @summary Test for JapaneseEra.of(int)
 * @run main JapaneseEraWitupTest_P8ae551a4977b
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest_P8ae551a4977b {

    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }

    static void testValidEras() {
        // Known valid era values; assuming 1 and 2 as valid for testing
        try {
            JapaneseEra era1 = JapaneseEra.of(1);
            System.out.println("Valid era 1: " + era1);
        } catch (Exception e) {
            System.err.println("Unexpected exception for era 1: " + e);
        }

        try {
            JapaneseEra era2 = JapaneseEra.of(2);
            System.out.println("Valid era 2: " + era2);
        } catch (Exception e) {
            System.err.println("Unexpected exception for era 2: " + e);
        }
    }

    static void testInvalidEras() {
        int[] invalidEras = {0, -1, 999};
        for (int invalid : invalidEras) {
            try {
                JapaneseEra.of(invalid);
                System.err.println("Expected exception not thrown for invalid era: " + invalid);
            } catch (DateTimeException e) {
                System.out.println("Correctly caught exception for invalid era " + invalid + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected exception type for invalid era " + invalid + ": " + e);
            }
        }
    }
}
