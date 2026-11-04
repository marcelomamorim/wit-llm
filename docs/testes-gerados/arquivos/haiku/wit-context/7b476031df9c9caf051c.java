/*
 * @test
 * @summary Witup-derived behavioral checks for JapaneseDate.of(era, yearOfEra, month, dayOfMonth)
 * @run main JapaneseDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testNullEraThrowsNullPointerException();
        testHeiseiFirstValidDay();
        testShowaLastDay();
        testDayBeforeHeiseiStartIsRejected();
    }

    static void testNullEraThrowsNullPointerException() {
        try {
            JapaneseDate.of(null, 1, 1, 8);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null era");
    }

    static void testHeiseiFirstValidDay() {
        JapaneseDate d = JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 8);
        check(d.getEra() == JapaneseEra.HEISEI, "era mismatch for Heisei 1-01-08");
        long expected = LocalDate.of(1989, 1, 8).toEpochDay();
        check(d.toEpochDay() == expected, "epoch day mismatch for Heisei 1-01-08");
    }

    static void testShowaLastDay() {
        JapaneseDate d = JapaneseDate.of(JapaneseEra.SHOWA, 64, 1, 7);
        check(d.getEra() == JapaneseEra.SHOWA, "era mismatch for Showa 64-01-07");
        long expected = LocalDate.of(1989, 1, 7).toEpochDay();
        check(d.toEpochDay() == expected, "epoch day mismatch for Showa 64-01-07");
    }

    static void testDayBeforeHeiseiStartIsRejected() {
        try {
            JapaneseDate.of(JapaneseEra.HEISEI, 1, 1, 7);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for Heisei 1-01-07");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
