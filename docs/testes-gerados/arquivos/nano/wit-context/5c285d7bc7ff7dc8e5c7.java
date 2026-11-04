/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.ofLocalizedDateTime
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedDateTime_NullInput();
        testOfLocalizedDateTime_ValidInput();
    }

    public static void testOfLocalizedDateTime_NullInput() {
        try {
            DateTimeFormatter.ofLocalizedDateTime(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testOfLocalizedDateTime_ValidInput() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
            if (formatter == null) {
                System.err.println("Failed: Formatter should not be null for valid input");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid input: " + e);
        }
    }
}
