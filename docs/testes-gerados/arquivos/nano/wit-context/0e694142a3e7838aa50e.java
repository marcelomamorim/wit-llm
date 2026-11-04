/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.parse(CharSequence)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseWithNullInput();
        testParseWithValidInput();
        testParseWithInvalidInput();
    }

    private static void testParseWithNullInput() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            formatter.parse((CharSequence) null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    private static void testParseWithValidInput() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String dateStr = "2023-03-15";
            TemporalAccessor result = formatter.parse(dateStr);
            if (result == null) {
                System.err.println("Failed: parse returned null for valid input");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid input: " + e);
        }
    }

    private static void testParseWithInvalidInput() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String invalidDateStr = "not-a-date";
            formatter.parse(invalidDateStr);
            System.err.println("Failed: Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for invalid input: " + e);
        }
    }
}
