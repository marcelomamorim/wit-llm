/*
 * @test
 * @summary Deterministic tests for java.time.LocalDate.parse(CharSequence, DateTimeFormatter)
 * @run main java.time.LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseValid();
    }

    private static void testParseWithNullFormatter() {
        try {
            LocalDate.parse("2018-09-01", null);
            throw new RuntimeException("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate.parse(null, formatter);
            throw new RuntimeException("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testParseValid() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate date = LocalDate.parse("2018-09-01", formatter);
        if (!date.equals(LocalDate.of(2018, 9, 1))) {
            throw new RuntimeException("Parsed date does not match expected date");
        }
    }
}
