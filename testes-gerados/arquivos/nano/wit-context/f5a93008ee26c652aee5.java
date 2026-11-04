/*
 * @test
 * @summary Tests for java.time.LocalDate.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    public static void testParseWithNullFormatter() {
        try {
            LocalDate.parse("2023-03-15", null);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate.parse(null, formatter);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
