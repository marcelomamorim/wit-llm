/* @test
   @summary Tests the static method JapaneseEra.of(int).
   @run main JapaneseEraWitupTest_P3b8cb79bb9fd
 */
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;
public class JapaneseEraWitupTest_P3b8cb79bb9fd {
    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }
    static void testValidEras() {
        try {
            for (int eraValue = -1; eraValue <= 10; eraValue++) {
                try {
                    JapaneseEra era = JapaneseEra.of(eraValue);
                    if (era == null) {
                        throw new AssertionError("Expected non-null for era: " + eraValue);
                    }
                } catch (DateTimeException e) {
                    if (eraValue >= 0 && eraValue < JapaneseEra.KNOWN_ERAS.length) {
                        throw new AssertionError("Unexpected exception for valid era: " + eraValue, e);
                    }
                }
            }
        } catch (Exception e) {
            throw new AssertionError("Error in testValidEras", e);
        }
    }
    static void testInvalidEras() {
        int[] invalidEras = {-2, JapaneseEra.KNOWN_ERAS.length, 100};
        for (int invalid : invalidEras) {
            try {
                JapaneseEra.of(invalid);
                throw new AssertionError("Expected exception for invalid era: " + invalid);
            } catch (DateTimeException e) {
                // expected
            }
        }
    }
}
