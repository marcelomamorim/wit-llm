/*
 * @test
 * @summary Witup-derived tests for Instant.parse(CharSequence): null input and basic ISO-8601 parsing
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class InstantWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNullTextThrowsNPE() {
        try {
            Instant.parse(null);
            check(false, "Instant.parse(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEpochParsesToEpoch() {
        Instant parsed = Instant.parse("1970-01-01T00:00:00Z");
        check(parsed.equals(Instant.EPOCH), "epoch string should parse to Instant.EPOCH");
        check(parsed.getEpochSecond() == 0L, "epoch seconds should be 0");
        check(parsed.getNano() == 0, "epoch nanos should be 0");
    }

    static void testFractionalSecondsParsed() {
        Instant parsed = Instant.parse("1970-01-01T00:00:01.5Z");
        check(parsed.getEpochSecond() == 1L, "fractional epoch seconds should be 1");
        check(parsed.getNano() == 500000000, "fractional nanos should be 500000000");
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        try {
            Instant.parse("not-a-date");
            check(false, "invalid text should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testMissingOffsetThrowsDateTimeParseException() {
        try {
            Instant.parse("1970-01-01T00:00:00");
            check(false, "text without offset should throw DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testNullTextThrowsNPE();
        testEpochParsesToEpoch();
        testFractionalSecondsParsed();
        testInvalidTextThrowsDateTimeParseException();
        testMissingOffsetThrowsDateTimeParseException();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
