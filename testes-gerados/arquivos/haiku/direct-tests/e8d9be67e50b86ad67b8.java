/*
 * @test
 * @summary Verify JapaneseChronology.eraOf returns the matching public JapaneseEra constant for valid era values
 * @run main JapaneseChronologyWitupTest
 */

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;

public class JapaneseChronologyWitupTest {

    public static void main(String[] args) {
        JapaneseChronology chrono = JapaneseChronology.INSTANCE;

        checkEra(chrono, JapaneseEra.MEIJI);
        checkEra(chrono, JapaneseEra.TAISHO);
        checkEra(chrono, JapaneseEra.SHOWA);
        checkEra(chrono, JapaneseEra.HEISEI);

        JapaneseEra fromMeiji = chrono.eraOf(JapaneseEra.MEIJI.getValue());
        check(fromMeiji == JapaneseEra.MEIJI, "eraOf(MEIJI value) must return MEIJI");

        JapaneseEra fromHeisei = chrono.eraOf(JapaneseEra.HEISEI.getValue());
        check(fromHeisei == JapaneseEra.HEISEI, "eraOf(HEISEI value) must return HEISEI");
        check(fromHeisei != JapaneseEra.SHOWA, "eraOf(HEISEI value) must not return SHOWA");
    }

    private static void checkEra(JapaneseChronology chrono, JapaneseEra expected) {
        JapaneseEra actual = chrono.eraOf(expected.getValue());
        check(actual == expected,
              "eraOf(" + expected.getValue() + ") returned " + actual + ", expected " + expected);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
