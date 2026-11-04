/*
 * @test
 * @summary Tests LocalDate.parse(CharSequence) ISO parsing behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidParse();
        testCharSequenceImplementation();
        testToStringRoundTrip();
        testInvalidDayOfMonthThrows();
        testNonPaddedMonthThrows();
        testTrailingTextThrows();
        testEmptyTextThrows();
        testNullTextThrows();
    }

    static void testValidParse() {
        LocalDate actual = LocalDate.parse("2020-02-29");
        check(actual.equals(LocalDate.of(2020, 2, 29)), "leap day should parse");
        check(actual.getYear() == 2020, "year should be 2020");
        check(actual.getMonthValue() == 2, "month should be 2");
        check(actual.getDayOfMonth() == 29, "day should be 29");
    }

    static void testCharSequenceImplementation() {
        LocalDate fromBuilder = LocalDate.parse(new StringBuilder("1999-12-31"));
        check(fromBuilder.equals(LocalDate.of(1999, 12, 31)), "StringBuilder input should parse like String");
    }

    static void testToStringRoundTrip() {
        LocalDate actual = LocalDate.parse("2000-01-01");
        check(actual.toString().equals("2000-01-01"), "toString should round-trip parsed value");
        check(LocalDate.parse(actual.toString()).equals(actual), "parse(toString()) should be equal");
    }

    static void testInvalidDayOfMonthThrows() {
        expectParseException("2019-02-29");
    }

    static void testNonPaddedMonthThrows() {
        expectParseException("2020-1-01");
    }

    static void testTrailingTextThrows() {
        expectParseException("2020-02-29x");
    }

    static void testEmptyTextThrows() {
        expectParseException("");
    }

    static void testNullTextThrows() {
        try {
            LocalDate.parse((CharSequence) null);
            throw new RuntimeException("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void expectParseException(String text) {
        try {
            LocalDate result = LocalDate.parse(text);
            throw new RuntimeException("expected DateTimeParseException for '" + text + "' but got " + result);
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
