/*
 * @test
 * @summary Test java.time.LocalDate.parse(CharSequence, DateTimeFormatter) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testParseValidDate();
        testParseNullFormatter();
        testParseInvalidDate();
    }

    private static void testParseValidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String text = "2018-09-01";
        LocalDate date = LocalDate.parse(text, formatter);
        if (!date.equals(LocalDate.of(2018, 9, 1))) {
            throw new AssertionError("Parsed date does not match expected date");
        }
    }

    private static void testParseNullFormatter() {
        try {
            LocalDate.parse("2018-09-01", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testParseInvalidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String invalidText = "2018-02-30"; // invalid date
        try {
            LocalDate.parse(invalidText, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid date");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
