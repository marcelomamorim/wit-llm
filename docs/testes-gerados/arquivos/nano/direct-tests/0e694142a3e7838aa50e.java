/*
 * @test
 * @summary Test DateTimeFormatter.parse(CharSequence) method for various inputs.
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testValidDateString();
        testInvalidDateString();
        testNullInput();
    }

    static void testValidDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateStr = "2023-03-15";
        try {
            TemporalAccessor result = formatter.parse(dateStr);
            if (result == null) {
                throw new AssertionError("Parsing valid date returned null");
            }
        } catch (Exception e) {
            throw new AssertionError("Parsing valid date threw exception: " + e);
        }
    }

    static void testInvalidDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String invalidDateStr = "2023-02-30"; // invalid date
        try {
            formatter.parse(invalidDateStr);
            // parse() does not validate date correctness, so no exception expected here
        } catch (DateTimeParseException e) {
            // Should not reach here for invalid date, as parse() may accept invalid date strings
            throw new AssertionError("Parsing invalid date threw exception: " + e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid date: " + e);
        }
    }

    static void testNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.parse((CharSequence) null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null input: " + e);
        }
    }
}
