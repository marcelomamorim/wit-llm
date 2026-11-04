/*
 * @test
 * @summary Test ZonedDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithValidInput();
        testParseWithInvalidInput();
        testParseWithNullFormatter();
    }

    static void testParseWithValidInput() {
        String dateTimeString = "2023-03-25T10:15:30+01:00[Europe/Paris]";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
        ZonedDateTime result = ZonedDateTime.parse(dateTimeString, formatter);
        assert result != null : "Parsed ZonedDateTime should not be null";
        assert result.toString().equals("2023-03-25T10:15:30+01:00[Europe/Paris]") : "Unexpected parse result: " + result;
    }

    static void testParseWithInvalidInput() {
        String invalidDateTimeString = "invalid-date-time";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
        try {
            ZonedDateTime.parse(invalidDateTimeString, formatter);
            assert false : "Expected DateTimeParseException was not thrown";
        } catch (DateTimeParseException e) {
            // Expected exception
        }
    }

    static void testParseWithNullFormatter() {
        String dateTimeString = "2023-03-25T10:15:30+01:00[Europe/Paris]";
        try {
            ZonedDateTime.parse(dateTimeString, null);
            assert false : "Expected NullPointerException was not thrown";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
