/*
 * @test
 * @summary Witup-generated tests for LocalTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidParseIso();
        testValidParsePattern();
        testNullFormatterThrowsNpe();
        testNullTextThrowsNpe();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void testValidParseIso() {
        LocalTime actual = LocalTime.parse("10:15:30", DateTimeFormatter.ISO_LOCAL_TIME);
        check(LocalTime.of(10, 15, 30).equals(actual), "ISO parse mismatch: " + actual);
    }

    static void testValidParsePattern() {
        LocalTime actual = LocalTime.parse("10:15", DateTimeFormatter.ofPattern("HH:mm"));
        check(LocalTime.of(10, 15).equals(actual), "pattern parse mismatch: " + actual);
    }

    static void testNullFormatterThrowsNpe() {
        boolean thrown = false;
        try {
            LocalTime.parse("10:15", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null formatter");
    }

    static void testNullTextThrowsNpe() {
        boolean thrown = false;
        try {
            LocalTime.parse(null, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null text");
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        boolean thrown = false;
        try {
            LocalTime.parse("not-a-time", DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException for invalid text");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
