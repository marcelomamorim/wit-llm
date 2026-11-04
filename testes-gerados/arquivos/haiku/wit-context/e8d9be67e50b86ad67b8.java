/*
 * @test
 * @summary Witup-derived checks for JapaneseChronology.eraOf(int) valid and invalid era values
 * @run main JapaneseChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;

public class JapaneseChronologyWitupTest {

    public static void main(String[] args) {
        JapaneseChronology chrono = JapaneseChronology.INSTANCE;

        JapaneseEra meiji = chrono.eraOf(-1);
        if (meiji == null) {
            throw new RuntimeException("eraOf(-1) returned null");
        }
        if (meiji.getValue() != -1) {
            throw new RuntimeException("eraOf(-1) returned value " + meiji.getValue());
        }

        JapaneseEra heisei = chrono.eraOf(2);
        if (heisei == null || heisei.getValue() != 2) {
            throw new RuntimeException("eraOf(2) returned unexpected era");
        }

        checkInvalid(chrono, -2);
        checkInvalid(chrono, 100);
        checkInvalid(chrono, Integer.MIN_VALUE);
    }

    private static void checkInvalid(JapaneseChronology chrono, int eraValue) {
        try {
            JapaneseEra era = chrono.eraOf(eraValue);
            throw new RuntimeException("eraOf(" + eraValue + ") did not throw, returned " + era);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
