/*
 * @test
 * @summary Test java.time.LocalDateTime.parse(CharSequence, DateTimeFormatter) for null inputs and normal parsing
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInputs();
    }

    private static void testParseWithNullFormatter() {
        try {
            LocalDateTime.parse("2020-01-01T12:00", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            LocalDateTime.parse(null, formatter);
            throw new AssertionError("Expected DateTimeParseException or NullPointerException for null text");
        } catch (DateTimeParseException | NullPointerException e) {
            // Accept either exception as valid behavior
        }
    }

    private static void testParseWithValidInputs() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String validDateTime = "2018-12-03T10:15:30";
        LocalDateTime ldt = LocalDateTime.parse(validDateTime, formatter);
        if (ldt.getYear() != 2018 || ldt.getMonthValue() != 12 || ldt.getDayOfMonth() != 3 ||
            ldt.getHour() != 10 || ldt.getMinute() != 15 || ldt.getSecond() != 30) {
            throw new AssertionError("Parsed LocalDateTime does not match expected values");
        }
    }
}
