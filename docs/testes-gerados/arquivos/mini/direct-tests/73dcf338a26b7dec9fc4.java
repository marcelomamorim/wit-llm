/*
 * @test
 * @summary Test java.time.format.DateTimeFormatter.parse(CharSequence, ParsePosition) method
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testParseNullText();
        testParseNullPosition();
        testParseValidPosition();
        testParseInvalidPosition();
    }

    private static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse(null, new ParsePosition(0));
            throw new RuntimeException("Expected NullPointerException for null text");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseNullPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse("2018-09-01", null);
            throw new RuntimeException("Expected NullPointerException for null position");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseValidPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor result = formatter.parse("2018-09-01", pos);
        if (result == null) {
            throw new RuntimeException("Parse returned null for valid input");
        }
        if (pos.getErrorIndex() != -1) {
            throw new RuntimeException("ParsePosition error index should be -1 for valid parse");
        }
        if (pos.getIndex() <= 0) {
            throw new RuntimeException("ParsePosition index should advance after parse");
        }
    }

    private static void testParseInvalidPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        // Position index beyond text length should cause DateTimeParseException or IndexOutOfBoundsException
        ParsePosition pos = new ParsePosition(20);
        try {
            formatter.parse("2018-09-01", pos);
            throw new RuntimeException("Expected DateTimeParseException or IndexOutOfBoundsException for invalid position");
        } catch (DateTimeParseException | IndexOutOfBoundsException ex) {
            // expected
        }
    }
}
