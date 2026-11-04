/*
 * @test
 * @summary Behavioral tests for OffsetTime.parse(CharSequence) using the ISO offset time formatter
 * @run main OffsetTimeWitupTest
 */

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testParseWithNumericOffset() {
        OffsetTime actual = OffsetTime.parse("10:15:30+01:00");
        OffsetTime expected = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(1));
        check(expected.equals(actual), "parse numeric offset: expected " + expected + " but was " + actual);
    }

    static void testParseWithZuluOffset() {
        OffsetTime actual = OffsetTime.parse("10:15:30Z");
        check(ZoneOffset.UTC.equals(actual.getOffset()), "parse Z offset: got " + actual.getOffset());
        check(LocalTime.of(10, 15, 30).equals(actual.toLocalTime()), "parse Z local time: got " + actual.toLocalTime());
    }

    static void testParseWithNanos() {
        OffsetTime actual = OffsetTime.parse("10:15:30.123456789-05:00");
        OffsetTime expected = OffsetTime.of(10, 15, 30, 123456789, ZoneOffset.ofHours(-5));
        check(expected.equals(actual), "parse nanos: expected " + expected + " but was " + actual);
    }

    static void testParseWithoutSeconds() {
        OffsetTime actual = OffsetTime.parse("10:15+01:00");
        OffsetTime expected = OffsetTime.of(10, 15, 0, 0, ZoneOffset.ofHours(1));
        check(expected.equals(actual), "parse without seconds: expected " + expected + " but was " + actual);
    }

    static void testRoundTripToString() {
        OffsetTime actual = OffsetTime.parse("10:15:30+01:00");
        check("10:15:30+01:00".equals(actual.toString()), "toString round trip: got " + actual);
    }

    static void testParseMissingOffsetFails() {
        boolean thrown = false;
        try {
            OffsetTime.parse("10:15:30");
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "parse without offset should throw DateTimeParseException");
    }

    static void testParseInvalidTextFails() {
        boolean thrown = false;
        try {
            OffsetTime.parse("not-a-time");
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "parse invalid text should throw DateTimeParseException");
    }

    public static void main(String[] args) {
        testParseWithNumericOffset();
        testParseWithZuluOffset();
        testParseWithNanos();
        testParseWithoutSeconds();
        testRoundTripToString();
        testParseMissingOffsetFails();
        testParseInvalidTextFails();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
