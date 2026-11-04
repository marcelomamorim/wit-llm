/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.ofPattern(String, Locale)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullPattern();
        testNullLocaleWithEmptyPattern();
    }

    private static void testNullPattern() {
        try {
            DateTimeFormatter.ofPattern(null, Locale.US);
            System.err.println("Expected NullPointerException for null pattern, but none was thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNullLocaleWithEmptyPattern() {
        String pattern = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, null);
            if (formatter == null) {
                System.err.println("Formatter should not be null for empty pattern with null locale.");
                System.exit(1);
            }
            // Additional check: formatter should be usable without exception
            formatter.format(java.time.LocalDate.of(2000, 1, 1));
        } catch (NullPointerException e) {
            System.err.println("Unexpected NullPointerException for empty pattern with null locale.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
