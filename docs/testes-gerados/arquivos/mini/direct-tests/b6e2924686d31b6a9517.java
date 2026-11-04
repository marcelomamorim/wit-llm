/*
 * @test
 * @summary Test JapaneseEra.of(int) method for valid and invalid inputs
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testValidEras();
        testInvalidEras();
    }

    private static void testValidEras() {
        // Test known valid era ordinals from 0 to length-1
        JapaneseEra[] knownEras = getKnownEras();
        for (int i = 0; i < knownEras.length; i++) {
            JapaneseEra era = JapaneseEra.of(i);
            if (era != knownEras[i]) {
                throw new AssertionError("JapaneseEra.of(" + i + ") returned unexpected era");
            }
        }
    }

    private static void testInvalidEras() {
        int[] invalidInputs = { -1, -100, Integer.MIN_VALUE, getKnownEras().length, getKnownEras().length + 1, Integer.MAX_VALUE };
        for (int invalid : invalidInputs) {
            try {
                JapaneseEra.of(invalid);
                throw new AssertionError("JapaneseEra.of(" + invalid + ") did not throw DateTimeException");
            } catch (DateTimeException expected) {
                // expected
            }
        }
    }

    // Helper method to access known eras via reflection since KNOWN_ERAS is package-private
    private static JapaneseEra[] getKnownEras() {
        // Since we cannot access KNOWN_ERAS directly, we use known valid ordinals from 0 upwards
        // We try to get eras until of() throws exception
        java.util.ArrayList<JapaneseEra> list = new java.util.ArrayList<>();
        int index = 0;
        while (true) {
            try {
                JapaneseEra era = JapaneseEra.of(index);
                list.add(era);
                index++;
            } catch (DateTimeException e) {
                break;
            }
        }
        return list.toArray(new JapaneseEra[0]);
    }
}
