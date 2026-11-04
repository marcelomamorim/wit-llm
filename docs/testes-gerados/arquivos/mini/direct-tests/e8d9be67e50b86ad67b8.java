/*
 * @test
 * @summary Test JapaneseChronology.eraOf(int) method
 * @run main JapaneseChronologyWitupTest
 */

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseChronologyWitupTest {
    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        // JapaneseEra.of supports era values from -1 (Meiji) to current eras
        // Test known valid era values
        JapaneseChronology chrono = JapaneseChronology.INSTANCE;

        // Test era 1 (Showa) - known valid era
        JapaneseEra era1 = chrono.eraOf(1);
        if (era1.getValue() != 1) {
            throw new AssertionError("Expected era value 1, got " + era1.getValue());
        }

        // Test era 0 (Taisho) - known valid era
        JapaneseEra era0 = chrono.eraOf(0);
        if (era0.getValue() != 0) {
            throw new AssertionError("Expected era value 0, got " + era0.getValue());
        }

        // Test era -1 (Meiji) - known valid era
        JapaneseEra eraMinus1 = chrono.eraOf(-1);
        if (eraMinus1.getValue() != -1) {
            throw new AssertionError("Expected era value -1, got " + eraMinus1.getValue());
        }
    }

    private static void testInvalidEra() {
        JapaneseChronology chrono = JapaneseChronology.INSTANCE;
        boolean thrown = false;
        try {
            chrono.eraOf(9999); // invalid era value
        } catch (DateTimeException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected DateTimeException for invalid era value");
        }
    }
}
