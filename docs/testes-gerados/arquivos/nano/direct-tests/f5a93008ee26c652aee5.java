/*
 * @test
 * @summary Test LocalDate.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseNullFormatter();
        testParseInvalidFormat();
    }

    public static void testParseValidInput() {
        String dateStr = "2023-03-15";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate result = LocalDate.parse(dateStr, formatter);
        assert result.equals(LocalDate.of(2023, 3, 15)) : "Parsing valid date failed";
    }

    public static void testParseNullFormatter() {
        String dateStr = "2023-03-15";
        try {
            LocalDate.parse(dateStr, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    public static void testParseInvalidFormat() {
        String invalidDateStr = "15-03-2023";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            LocalDate.parse(invalidDateStr, formatter);
            assert false : "Expected DateTimeParseException";
        } catch (DateTimeParseException e) {
            // expected
        }
    }
}
