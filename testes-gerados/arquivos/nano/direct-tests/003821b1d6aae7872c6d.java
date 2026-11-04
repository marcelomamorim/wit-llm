/*
 * @test
 * @summary Test parseObject method of DateTimeFormatter for various inputs
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.text.ParseException;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseObjectWithValidInput();
        testParseObjectWithNullInput();
        testParseObjectWithInvalidInput();
    }

    static void testParseObjectWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "2023-03-15";
        try {
            Object result = formatter.parseObject(input);
            if (!(result instanceof java.time.temporal.TemporalAccessor)) {
                throw new AssertionError("Expected TemporalAccessor instance");
            }
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for valid input", e);
        }
    }

    static void testParseObjectWithNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parseObject(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for null input", e);
        }
    }

    static void testParseObjectWithInvalidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String input = "invalid-date";
        try {
            formatter.parseObject(input);
            throw new AssertionError("Expected ParseException for invalid input");
        } catch (ParseException e) {
            // expected
        }
    }
}
