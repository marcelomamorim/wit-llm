/*
 * @test
 * @summary Verify DateTimeFormatter.parse(CharSequence, ParsePosition) null checks and basic parsing behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullText();
        testNullPosition();
        testValidParse();
        testInvalidParse();
    }

    static void testNullText() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(null, new ParsePosition(0));
            throw new RuntimeException("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullPosition() {
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-02", null);
            throw new RuntimeException("expected NullPointerException for null position");
        } catch (NullPointerException expected) {
        }
    }

    static void testValidParse() {
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor result = DateTimeFormatter.ISO_LOCAL_DATE.parse("2020-01-02", pos);
        if (result == null) {
            throw new RuntimeException("expected non-null result for valid input");
        }
        if (pos.getIndex() != 10) {
            throw new RuntimeException("unexpected index after parse: " + pos.getIndex());
        }
        if (pos.getErrorIndex() != -1) {
            throw new RuntimeException("unexpected error index: " + pos.getErrorIndex());
        }
    }

    static void testInvalidParse() {
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor result = DateTimeFormatter.ISO_LOCAL_DATE.parse("abc", pos);
        if (result != null) {
            throw new RuntimeException("expected null for invalid input");
        }
        if (pos.getErrorIndex() < 0) {
            throw new RuntimeException("expected error index to be set for invalid input");
        }
    }
}
