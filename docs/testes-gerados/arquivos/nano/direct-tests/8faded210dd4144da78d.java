/*
 * @test
 * @summary Tests for LocalDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseNullFormatter();
        testParseInvalidFormat();
    }

    public static void testParseValidInput() {
        try {
            String dateTimeStr = "2023-03-25T15:30:45";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime result = LocalDateTime.parse(dateTimeStr, formatter);
            assert result != null : "Parsed LocalDateTime should not be null";
            assert result.getYear() == 2023 : "Year should be 2023";
            assert result.getMonthValue() == 3 : "Month should be 3";
            assert result.getDayOfMonth() == 25 : "Day should be 25";
            assert result.getHour() == 15 : "Hour should be 15";
            assert result.getMinute() == 30 : "Minute should be 30";
            assert result.getSecond() == 45 : "Second should be 45";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseValidInput", e);
        }
    }

    public static void testParseNullFormatter() {
        try {
            String dateTimeStr = "2023-03-25T15:30:45";
            LocalDateTime.parse(dateTimeStr, null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseNullFormatter", e);
        }
    }

    public static void testParseInvalidFormat() {
        try {
            String invalidStr = "InvalidDateTime";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime.parse(invalidStr, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseInvalidFormat", e);
        }
    }
}
