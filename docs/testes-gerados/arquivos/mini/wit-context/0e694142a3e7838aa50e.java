/*
 * @test
 * @summary Test parse(CharSequence) method of DateTimeFormatter for null input and normal parsing
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testParseNullInput();
        testParseValidInput();
    }

    private static void testParseNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException ex) {
            // Expected exception
        }
    }

    private static void testParseValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String validDate = "2018-09-01";
        TemporalAccessor result = null;
        try {
            result = formatter.parse(validDate);
        } catch (DateTimeParseException ex) {
            throw new AssertionError("Unexpected DateTimeParseException for valid input", ex);
        }
        if (result == null) {
            throw new AssertionError("parse returned null for valid input");
        }
    }
}
