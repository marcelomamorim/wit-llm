/*
 * @test
 * @summary Tests DateTimeFormatter.parseObject(String, ParsePosition) through the public Format returned by toFormat()
 * @run main DateTimeFormatterWitupTest
 */

import java.text.Format;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Format format = f.toFormat();

        testSuccessfulParse(format);
        testNullTextThrowsNPE(format);
        testInvalidTextReturnsNullAndSetsErrorIndex(format);
        testParseWithStartOffset(format);
        testPositionBeyondLengthReturnsNull(format);
    }

    static void testSuccessfulParse(Format format) {
        ParsePosition pos = new ParsePosition(0);
        Object result = format.parseObject("2012-06-30", pos);
        check(result != null, "parse of valid text should return non-null");
        check(result instanceof TemporalAccessor, "result should be a TemporalAccessor");
        TemporalAccessor ta = (TemporalAccessor) result;
        check(ta.getLong(ChronoField.YEAR) == 2012L, "year should be 2012");
        check(ta.getLong(ChronoField.MONTH_OF_YEAR) == 6L, "month should be 6");
        check(ta.getLong(ChronoField.DAY_OF_MONTH) == 30L, "day should be 30");
        check(pos.getIndex() == 10, "index should advance to end of text");
        check(pos.getErrorIndex() == -1, "error index should remain -1 on success");
    }

    static void testNullTextThrowsNPE(Format format) {
        boolean thrown = false;
        try {
            format.parseObject(null, new ParsePosition(0));
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null text should throw NullPointerException");
    }

    static void testInvalidTextReturnsNullAndSetsErrorIndex(Format format) {
        ParsePosition pos = new ParsePosition(0);
        Object result = format.parseObject("abc", pos);
        check(result == null, "invalid text should return null");
        check(pos.getErrorIndex() >= 0, "error index should be set on failure");
    }

    static void testParseWithStartOffset(Format format) {
        ParsePosition pos = new ParsePosition(2);
        Object result = format.parseObject("xx2012-06-30", pos);
        check(result != null, "parse with offset should succeed");
        TemporalAccessor ta = (TemporalAccessor) result;
        check(ta.getLong(ChronoField.DAY_OF_MONTH) == 30L, "day should be 30 with offset");
        check(pos.getIndex() == 12, "index should advance to 12");
        check(pos.getErrorIndex() == -1, "error index should remain -1 on success");
    }

    static void testPositionBeyondLengthReturnsNull(Format format) {
        ParsePosition pos = new ParsePosition(100);
        Object result = format.parseObject("2012-06-30", pos);
        check(result == null, "position beyond text length should return null");
        check(pos.getErrorIndex() >= 0, "error index should be set when returning null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
