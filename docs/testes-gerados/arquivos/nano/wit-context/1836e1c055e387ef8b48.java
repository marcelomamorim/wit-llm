/*
 * @test
 * @summary Tests for DateTimeFormatter.ofPattern(String pattern)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullPattern();
        testValidPattern();
    }

    private static void testNullPattern() {
        try {
            DateTimeFormatter.ofPattern(null);
            System.err.println("Failed: Expected NullPointerException for null pattern");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null pattern");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null pattern: " + e);
        }
    }

    private static void testValidPattern() {
        String pattern = "yyyy-MM-dd";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            if (formatter != null) {
                System.out.println("Passed: Formatter created with pattern: " + pattern);
            } else {
                System.err.println("Failed: Formatter is null for pattern: " + pattern);
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception thrown for valid pattern: " + e);
        }
    }
}
