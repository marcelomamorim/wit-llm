/*
 * @test
 * @summary Test DateTimeFormatter.ofPattern(String) for valid and invalid patterns
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeFormatterWitupTest {
    public static void main(String[] args) {
        testValidPattern();
        testInvalidPattern();
    }

    private static void testValidPattern() {
        // Basic valid pattern
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted = dtf.format(java.time.LocalDate.of(2020, 12, 31));
        if (!"2020-12-31".equals(formatted)) {
            throw new AssertionError("Expected formatted date to be 2020-12-31 but was " + formatted);
        }
    }

    private static void testInvalidPattern() {
        // Invalid pattern should throw IllegalArgumentException
        try {
            DateTimeFormatter.ofPattern("invalid-pattern-#");
            throw new AssertionError("Expected IllegalArgumentException for invalid pattern");
        } catch (IllegalArgumentException ex) {
            // Expected
        }
    }
}
