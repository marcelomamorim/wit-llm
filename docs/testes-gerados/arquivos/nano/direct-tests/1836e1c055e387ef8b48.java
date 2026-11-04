/*
 * @test
 * @summary Tests for DateTimeFormatter.ofPattern(String pattern)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testValidPattern();
        testEmptyPattern();
        testNullPattern();
    }

    static void testValidPattern() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (formatter == null) {
            throw new AssertionError("Formatter should not be null for valid pattern");
        }
    }

    static void testEmptyPattern() {
        String pattern = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (formatter == null) {
            throw new AssertionError("Formatter should not be null for empty pattern");
        }
    }

    static void testNullPattern() {
        try {
            DateTimeFormatter.ofPattern(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
