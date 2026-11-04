/*
 * @test
 * @summary Test LocalDateTime.parse(CharSequence) method for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseInvalidInput();
    }

    static void testParseValidInput() {
        String validInput = "2023-03-25T15:30:45";
        try {
            LocalDateTime result = LocalDateTime.parse(validInput);
            if (result == null) {
                throw new AssertionError("Parsed result is null for valid input");
            }
            // Additional check: verify the parsed date matches expected values
            if (result.getYear() != 2023 || result.getMonthValue() != 3 || result.getDayOfMonth() != 25 || result.getHour() != 15 || result.getMinute() != 30 || result.getSecond() != 45) {
                throw new AssertionError("Parsed date components do not match expected values");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception thrown during valid input parsing: " + e);
        }
    }

    static void testParseInvalidInput() {
        String invalidInput = "invalid-date-time";
        try {
            LocalDateTime.parse(invalidInput);
            throw new AssertionError("Expected DateTimeParseException not thrown for invalid input");
        } catch (DateTimeParseException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type thrown: " + e);
        }
    }
}
