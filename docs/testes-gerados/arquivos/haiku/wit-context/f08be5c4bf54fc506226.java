/*
 * @test
 * @summary White-box-derived tests for LocalDate.parse(CharSequence): valid ISO input, null input and malformed input
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void testValidIsoParse() {
        LocalDate d = LocalDate.parse("2012-06-30");
        check(d.equals(LocalDate.of(2012, 6, 30)), "parse(\"2012-06-30\") should equal LocalDate.of(2012, 6, 30)");
    }

    private static void testValidIsoParseWithCharSequence() {
        LocalDate d = LocalDate.parse(new StringBuilder("1999-12-31"));
        check(d.equals(LocalDate.of(1999, 12, 31)), "parse(StringBuilder) should accept any CharSequence");
    }

    private static void testNullTextThrowsNpe() {
        try {
            LocalDate.parse((CharSequence) null);
            check(false, "parse(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInvalidMonthThrowsParseException() {
        try {
            LocalDate.parse("2012-13-01");
            check(false, "parse with month 13 should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    private static void testEmptyTextThrowsParseException() {
        try {
            LocalDate.parse("");
            check(false, "parse(\"\") should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    private static void testTrailingGarbageThrowsParseException() {
        try {
            LocalDate.parse("2012-06-30x");
            check(false, "parse with trailing characters should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testValidIsoParse();
        testValidIsoParseWithCharSequence();
        testNullTextThrowsNpe();
        testInvalidMonthThrowsParseException();
        testEmptyTextThrowsParseException();
        testTrailingGarbageThrowsParseException();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
