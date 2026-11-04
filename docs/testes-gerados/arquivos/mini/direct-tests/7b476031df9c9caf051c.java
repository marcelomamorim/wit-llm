/*
 * @test
 * @summary Test JapaneseDate.of(JapaneseEra, int, int, int) method
 * @run main JapaneseDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;

public class JapaneseDateWitupTest {
    public static void main(String[] args) {
        testValidDate();
        testNullEra();
        testInvalidDate();
    }

    private static void testValidDate() {
        // Use a known JapaneseEra instance
        JapaneseEra era = JapaneseEra.HEISEI;
        int yearOfEra = 1;
        int month = 1;
        int day = 8;

        JapaneseDate jd = JapaneseDate.of(era, yearOfEra, month, day);
        if (jd == null) {
            throw new AssertionError("JapaneseDate.of returned null for valid input");
        }
        // Check that the returned date has the expected era and yearOfEra
        if (!jd.getEra().equals(era)) {
            throw new AssertionError("Era mismatch");
        }
        if (jd.get(YEAR) != yearOfEra) {
            throw new AssertionError("Year of era mismatch");
        }
        if (jd.get(MONTH_OF_YEAR) != month) {
            throw new AssertionError("Month mismatch");
        }
        if (jd.get(DAY_OF_MONTH) != day) {
            throw new AssertionError("Day mismatch");
        }
    }

    private static void testNullEra() {
        try {
            JapaneseDate.of(null, 1, 1, 1);
            throw new AssertionError("Expected NullPointerException for null era");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidDate() {
        JapaneseEra era = JapaneseEra.HEISEI;
        // Invalid month (e.g. 13)
        try {
            JapaneseDate.of(era, 1, 13, 1);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (DateTimeException expected) {
            // expected
        }

        // Invalid day (e.g. 32)
        try {
            JapaneseDate.of(era, 1, 1, 32);
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (DateTimeException expected) {
            // expected
        }

        // Invalid yearOfEra (e.g. 0 or negative)
        try {
            JapaneseDate.of(era, 0, 1, 1);
            throw new AssertionError("Expected DateTimeException for invalid yearOfEra");
        } catch (DateTimeException expected) {
            // expected
        }
    }

    private static final java.time.temporal.ChronoField YEAR = java.time.temporal.ChronoField.YEAR;
    private static final java.time.temporal.ChronoField MONTH_OF_YEAR = java.time.temporal.ChronoField.MONTH_OF_YEAR;
    private static final java.time.temporal.ChronoField DAY_OF_MONTH = java.time.temporal.ChronoField.DAY_OF_MONTH;
}
