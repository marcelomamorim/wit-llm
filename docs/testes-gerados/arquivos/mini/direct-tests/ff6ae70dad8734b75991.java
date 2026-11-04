/*
 * @test
 * @summary Test java.time.Year.parse(CharSequence, DateTimeFormatter) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearWitupTest {
    public static void main(String[] args) {
        testParseValidYear();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValidYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu");
        Year y = Year.parse("2018", formatter);
        if (y.getValue() != 2018) {
            throw new AssertionError("Expected year 2018 but got " + y.getValue());
        }
    }

    private static void testParseNullFormatter() {
        try {
            Year.parse("2018", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu");
        try {
            Year.parse("invalid", formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
