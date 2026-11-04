/*
 * @test
 * @summary Test DateTimeFormatter.parse(CharSequence, ParsePosition) for various inputs
 * @run main DateTimeFormatterWitupTest
 */
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.ParsePosition;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseWithValidInput();
        testParseWithNullText();
        testParseWithNullPosition();
        testParseWithInvalidInput();
    }

    static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "2023-03-15";
        ParsePosition pos = new ParsePosition(0);
        try {
            TemporalAccessor result = formatter.parse(input, pos);
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid input");
            }
            if (pos.getIndex() != input.length()) {
                throw new AssertionError("ParsePosition index not at end after parsing valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        try {
            formatter.parse(null, pos);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseWithNullPosition() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "2023-03-15";
        try {
            formatter.parse(input, null);
            throw new AssertionError("Expected NullPointerException for null position");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseWithInvalidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "invalid-date";
        ParsePosition pos = new ParsePosition(0);
        try {
            formatter.parse(input, pos);
            if (pos.getErrorIndex() == -1) {
                throw new AssertionError("Expected error index set for invalid input");
            }
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            // acceptable exceptions for invalid input
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid input: " + e);
        }
    }
}
