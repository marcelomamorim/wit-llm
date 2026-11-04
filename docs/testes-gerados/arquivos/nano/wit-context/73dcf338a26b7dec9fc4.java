/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.parse(CharSequence, ParsePosition)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.ParsePosition;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParse_NullText();
        testParse_NullPosition();
        testParse_ValidInput();
        testParse_NullTextAndPosition();
    }

    public static void testParse_NullText() {
        ParsePosition pos = new ParsePosition(0);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse(null, pos);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    public static void testParse_NullPosition() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse("2023-03-25", (ParsePosition) null);
            throw new AssertionError("Expected NullPointerException for null position");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    public static void testParse_ValidInput() {
        ParsePosition pos = new ParsePosition(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse("2023-03-25", pos);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    public static void testParse_NullTextAndPosition() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse(null, null);
            throw new AssertionError("Expected NullPointerException for null text and position");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
