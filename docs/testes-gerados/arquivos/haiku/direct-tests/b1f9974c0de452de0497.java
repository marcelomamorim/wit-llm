/*
 * @test
 * @summary Tests for OffsetTime.parse(CharSequence, DateTimeFormatter) observable behavior
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testIsoOffsetParse() {
        OffsetTime actual = OffsetTime.parse("10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_TIME);
        OffsetTime expected = OffsetTime.of(10, 15, 30, 0, ZoneOffset.ofHours(1));
        check(actual.equals(expected), "ISO offset parse mismatch: " + actual);
    }

    static void testIsoOffsetParseWithNanos() {
        OffsetTime actual = OffsetTime.parse("10:15:30.123456789-05:30", DateTimeFormatter.ISO_OFFSET_TIME);
        OffsetTime expected = OffsetTime.of(10, 15, 30, 123456789, ZoneOffset.ofHoursMinutes(-5, -30));
        check(actual.equals(expected), "nanos parse mismatch: " + actual);
    }

    static void testUtcZParse() {
        OffsetTime actual = OffsetTime.parse("00:00:00Z", DateTimeFormatter.ISO_OFFSET_TIME);
        check(actual.equals(OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC)), "Z parse mismatch: " + actual);
        check(actual.getOffset().getTotalSeconds() == 0, "offset should be zero");
    }

    static void testCustomPatternParse() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm XXX");
        OffsetTime actual = OffsetTime.parse("23:59 +02:00", f);
        check(actual.equals(OffsetTime.of(23, 59, 0, 0, ZoneOffset.ofHours(2))),
              "custom pattern parse mismatch: " + actual);
    }

    static void testNullFormatterThrowsNpe() {
        boolean thrown = false;
        try {
            OffsetTime.parse("10:15:30+01:00", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null formatter");
    }

    static void testMissingOffsetFails() {
        boolean thrown = false;
        try {
            OffsetTime.parse("10:15:30", DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException when offset is missing");
    }

    static void testInvalidTextFails() {
        boolean thrown = false;
        try {
            OffsetTime.parse("not a time", DateTimeFormatter.ISO_OFFSET_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException for invalid text");
    }

    public static void main(String[] args) {
        testIsoOffsetParse();
        testIsoOffsetParseWithNanos();
        testUtcZParse();
        testCustomPatternParse();
        testNullFormatterThrowsNpe();
        testMissingOffsetFails();
        testInvalidTextFails();
    }
}
