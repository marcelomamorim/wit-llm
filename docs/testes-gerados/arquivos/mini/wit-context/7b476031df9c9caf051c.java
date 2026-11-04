/*
 * @test
 * @summary Test JapaneseDate.of(JapaneseEra, int, int, int) for valid and invalid inputs
 * @run main JapaneseDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.util.Objects;

public class JapaneseDateWitupTest {
    public static void main(String[] args) {
        testNullEraThrows();
        testInvalidDateThrows();
        testValidDateCreation();
    }

    private static void testNullEraThrows() {
        try {
            JapaneseDate.of(null, 1, 1, 1);
            throw new AssertionError("Expected NullPointerException for null era");
        } catch (NullPointerException ex) {
            // expected
        }
    }

    private static void testInvalidDateThrows() {
        JapaneseEra era = JapaneseEra.HEISEI;
        // Use invalid month (e.g. 13) to trigger DateTimeException
        try {
            JapaneseDate.of(era, 1, 13, 1);
            throw new AssertionError("Expected DateTimeException for invalid month");
        } catch (DateTimeException ex) {
            // expected
        }
        // Use invalid day (e.g. 32) to trigger DateTimeException
        try {
            JapaneseDate.of(era, 1, 1, 32);
            throw new AssertionError("Expected DateTimeException for invalid day");
        } catch (DateTimeException ex) {
            // expected
        }
        // Use invalid yearOfEra (e.g. 0 or negative) to trigger DateTimeException
        try {
            JapaneseDate.of(era, 0, 1, 1);
            throw new AssertionError("Expected DateTimeException for invalid yearOfEra");
        } catch (DateTimeException ex) {
            // expected
        }
    }

    private static void testValidDateCreation() {
        JapaneseEra era = JapaneseEra.HEISEI;
        int yearOfEra = 1;
        int month = 1;
        int day = 8;
        JapaneseDate jd = JapaneseDate.of(era, yearOfEra, month, day);
        Objects.requireNonNull(jd);
        // Check that the returned JapaneseDate has expected era and yearOfEra
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

    private static final java.time.temporal.ChronoField YEAR = java.time.temporal.ChronoField.YEAR;
    private static final java.time.temporal.ChronoField MONTH_OF_YEAR = java.time.temporal.ChronoField.MONTH_OF_YEAR;
    private static final java.time.temporal.ChronoField DAY_OF_MONTH = java.time.temporal.ChronoField.DAY_OF_MONTH;
}
