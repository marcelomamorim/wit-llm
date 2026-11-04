/*
 * @test
 * @summary Tests for JapaneseDate.of method
 * @run main JapaneseDateWitupTest
 */

import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testNullEra();
        testInvalidDate();
    }

    static void testValidDate() {
        JapaneseEra era = JapaneseEra.HEISEI;
        int yearOfEra = 30;
        int month = 4;
        int dayOfMonth = 15;
        JapaneseDate date = JapaneseDate.of(era, yearOfEra, month, dayOfMonth);
        assert date != null : "Date should not be null";
        assert date.getEra() == era : "Era mismatch";
        assert date.getYear() == yearOfEra : "Year of era mismatch";
        assert date.getMonthValue() == month : "Month mismatch";
        assert date.getDayOfMonth() == dayOfMonth : "Day of month mismatch";
    }

    static void testNullEra() {
        try {
            JapaneseDate.of(null, 10, 5, 10);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidDate() {
        JapaneseEra era = JapaneseEra.HEISEI;
        int invalidYear = 1000; // assuming invalid for the era
        int month = 2;
        int dayOfMonth = 30;
        try {
            JapaneseDate.of(era, invalidYear, month, dayOfMonth);
            assert false : "Expected DateTimeException";
        } catch (DateTimeException e) {
            // expected
        }
    }
}
