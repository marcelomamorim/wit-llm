/*
 * @test
 * @summary Test JapaneseChronology.eraOf(int) method for valid and invalid era values
 * @run main JapaneseChronologyWitupTest
 */

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseChronologyWitupTest {
    public static void main(String[] args) {
        testValidEraValues();
        testInvalidEraValues();
        System.out.println("All tests passed.");
    }

    private static void testValidEraValues() {
        // Test known valid era values
        // JapaneseEra.of(int) supports era values from -1 (Meiji) upwards
        // We test a few valid values including boundary
        int[] validEras = { -1, 0, 1, 2, 3, 4, 5 };
        for (int eraValue : validEras) {
            JapaneseEra era = JapaneseChronology.INSTANCE.eraOf(eraValue);
            if (era == null) {
                throw new AssertionError("eraOf returned null for valid era value: " + eraValue);
            }
            if (era.getValue() != eraValue) {
                throw new AssertionError("eraOf returned era with wrong value. Expected: " + eraValue + ", got: " + era.getValue());
            }
        }
    }

    private static void testInvalidEraValues() {
        // Test era values that are invalid and should throw DateTimeException
        int[] invalidEras = { Integer.MIN_VALUE, -2, 6, 100, Integer.MAX_VALUE };
        for (int eraValue : invalidEras) {
            boolean thrown = false;
            try {
                JapaneseChronology.INSTANCE.eraOf(eraValue);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for invalid era value: " + eraValue);
            }
        }
    }
}
