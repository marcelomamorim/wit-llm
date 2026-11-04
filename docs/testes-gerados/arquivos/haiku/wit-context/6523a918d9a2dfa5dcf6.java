/*
 * @test
 * @summary Witup-derived checks for OffsetTime.parse(CharSequence): valid ISO offset inputs, invalid inputs and null text
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testValidParses();
        testInvalidInputsThrowDateTimeParseException();
        testNullTextThrowsNullPointerException();
    }

    static void testValidParses() {
        check(OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(1)),
              OffsetTime.parse("10:15:30+01:00"));
        check(OffsetTime.of(10, 15, 30, 123456789, ZoneOffset.ofHours(1)),
              OffsetTime.parse("10:15:30.123456789+01:00"));
        check(OffsetTime.of(23, 59, 59, 0, ZoneOffset.ofHours(-5)),
              OffsetTime.parse("23:59:59-05:00"));
        check(OffsetTime.of(10, 15, 30, 0, ZoneOffset.UTC),
              OffsetTime.parse(new StringBuilder("10:15:30Z")));
    }

    static void testInvalidInputsThrowDateTimeParseException() {
        expectDateTimeParseException("10:15:30");
        expectDateTimeParseException("not a time");
        expectDateTimeParseException("");
    }

    static void testNullTextThrowsNullPointerException() {
        CharSequence nullText = null;
        try {
            OffsetTime.parse(nullText);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("Expected NullPointerException for null text");
    }

    static void expectDateTimeParseException(String text) {
        try {
            OffsetTime.parse(text);
        } catch (DateTimeParseException expected) {
            return;
        }
        throw new AssertionError("Expected DateTimeParseException for: " + text);
    }

    static void check(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }
}
