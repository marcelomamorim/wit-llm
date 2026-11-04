/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.parseObject(String, ParsePosition) behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testParseObjectValidInput();
        testParseObjectNullText();
        testParseObjectInvalidPosition();
        testParseObjectUnparseableText();
    }

    private static void testParseObjectValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);
        ParsePosition pos = new ParsePosition(0);
        Object result = formatter.parseObject("2018-09-25", pos);
        if (!(result instanceof TemporalAccessor)) {
            throw new AssertionError("Expected TemporalAccessor result");
        }
        if (pos.getErrorIndex() != -1) {
            throw new AssertionError("Expected no error index, got: " + pos.getErrorIndex());
        }
        if (pos.getIndex() != "2018-09-25".length()) {
            throw new AssertionError("Expected parse position index at end, got: " + pos.getIndex());
        }
    }

    private static void testParseObjectNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);
        ParsePosition pos = new ParsePosition(0);
        try {
            formatter.parseObject(null, pos);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testParseObjectInvalidPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);
        // Use a ParsePosition with index beyond text length to provoke IndexOutOfBoundsException inside parseUnresolved0
        ParsePosition pos = new ParsePosition(100);
        Object result = formatter.parseObject("2018-09-25", pos);
        if (result != null) {
            throw new AssertionError("Expected null result for invalid parse position");
        }
        if (pos.getErrorIndex() != 0) {
            throw new AssertionError("Expected error index set to 0, got: " + pos.getErrorIndex());
        }
    }

    private static void testParseObjectUnparseableText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);
        ParsePosition pos = new ParsePosition(0);
        Object result = formatter.parseObject("invalid-date", pos);
        if (result != null) {
            throw new AssertionError("Expected null result for unparseable text");
        }
        if (pos.getErrorIndex() != 0) {
            throw new AssertionError("Expected error index set to 0 for unparseable text, got: " + pos.getErrorIndex());
        }
    }
}
