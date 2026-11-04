/*
 * @test
 * @summary Test parseObject method of DateTimeFormatter for various inputs
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.text.ParsePosition;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseObjectWithValidInput();
        testParseObjectWithNullInput();
        testParseObjectWithInvalidInput();
    }

    static void testParseObjectWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "2023-03-15";
        ParsePosition pos = new ParsePosition(0);
        Object result = formatter.parseObject(input, pos);
        assert result instanceof TemporalAccessor : "Expected TemporalAccessor instance";
        assert pos.getIndex() == input.length() : "Parse position should reach end";
    }

    static void testParseObjectWithNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parseObject(null, new ParsePosition(0));
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParseObjectWithInvalidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "invalid-date";
        ParsePosition pos = new ParsePosition(0);
        Object result = formatter.parseObject(input, pos);
        assert result == null : "Expected null for invalid input";
        assert pos.getErrorIndex() >= 0 : "Error index should be set";
    }
}
