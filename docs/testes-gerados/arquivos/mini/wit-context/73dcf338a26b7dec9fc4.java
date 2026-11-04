/*
 * @test
 * @summary Deterministic tests for java.time.format.DateTimeFormatter.parse(CharSequence, ParsePosition)
 * @run main DateTimeFormatterWitupTest
 */

import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testParseNullText();
        testParseNullPosition();
        testParseValidInput();
    }

    private static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse(null, new ParsePosition(0));
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException npe) {
            // Expected
            if (!"text".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testParseNullPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse("2020-01-01", null);
            throw new AssertionError("Expected NullPointerException for null position");
        } catch (NullPointerException npe) {
            // Expected
            if (!"position".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testParseValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ParsePosition pos = new ParsePosition(0);
        TemporalAccessor result = formatter.parse("2018-09-01", pos);
        if (result == null) {
            throw new AssertionError("parse returned null for valid input");
        }
        if (pos.getErrorIndex() != -1) {
            throw new AssertionError("ParsePosition error index set for valid input");
        }
        if (pos.getIndex() != "2018-09-01".length()) {
            throw new AssertionError("ParsePosition index not advanced correctly");
        }
    }
}
