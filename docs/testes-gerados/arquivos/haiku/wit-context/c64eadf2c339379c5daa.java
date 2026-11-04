/*
 * @test
 * @summary Witup-generated tests for OffsetDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testNullTextThrowsNPE();
        testValidParse();
        testInvalidTextThrowsDateTimeParseException();
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNullFormatterThrowsNPE() {
        boolean thrown = false;
        try {
            OffsetDateTime.parse("2007-12-03T10:15:30+01:00", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("Expected NullPointerException when formatter is null");
        }
    }

    static void testNullTextThrowsNPE() {
        boolean thrown = false;
        try {
            OffsetDateTime.parse(null, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("Expected NullPointerException when text is null");
        }
    }

    static void testValidParse() {
        OffsetDateTime expected =
                OffsetDateTime.of(2007, 12, 3, 10, 15, 30, 0, ZoneOffset.ofHours(1));
        OffsetDateTime actual =
                OffsetDateTime.parse("2007-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        if (!expected.equals(actual)) {
            fail("Unexpected parse result: " + actual);
        }
    }

    static void testInvalidTextThrowsDateTimeParseException() {
        boolean thrown = false;
        try {
            OffsetDateTime.parse("2007-12-03", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        if (!thrown) {
            fail("Expected DateTimeParseException for text without time and offset");
        }
    }
}
