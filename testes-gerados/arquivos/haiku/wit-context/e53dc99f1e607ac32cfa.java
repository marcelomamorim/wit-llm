/*
 * @test
 * @summary WITUP-derived tests for LocalDateTime.parse(CharSequence)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testNullTextThrowsNullPointerException();
        testValidIsoTextParses();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testNullTextThrowsNullPointerException() {
        try {
            LocalDateTime.parse(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null text");
    }

    static void testValidIsoTextParses() {
        LocalDateTime actual = LocalDateTime.parse("2020-01-02T03:04:05");
        LocalDateTime expected = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
        if (!expected.equals(actual)) {
            throw new RuntimeException("unexpected parse result: " + actual);
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        try {
            LocalDateTime.parse("not a date");
        } catch (DateTimeParseException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeParseException for invalid text");
    }
}
