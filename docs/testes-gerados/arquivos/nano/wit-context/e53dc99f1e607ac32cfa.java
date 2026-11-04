/*
 * @test
 * @summary Tests for java.time.LocalDateTime.parse(CharSequence)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullText();
        testParseWithNullFormatter();
    }

    private static void testParseWithNullText() {
        try {
            LocalDateTime.parse(null);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testParseWithNullFormatter() {
        CharSequence text = "2023-03-25T10:15:30";
        try {
            // Using parse with explicit formatter set to null (simulate scenario)
            LocalDateTime.parse(text, null);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
