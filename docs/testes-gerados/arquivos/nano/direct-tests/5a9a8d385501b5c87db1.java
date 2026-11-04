/*
 * @test
 * @summary Test the static parse method of ZonedDateTime with ISO_ZONED_DATE_TIME formatter
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseInvalidInput();
    }

    static void testParseValidInput() {
        String validInput = "2023-03-25T10:15:30+01:00[Europe/Paris]";
        try {
            ZonedDateTime zdt = ZonedDateTime.parse(validInput);
            if (zdt == null) {
                throw new AssertionError("Parsed ZonedDateTime is null for valid input");
            }
            System.out.println("testParseValidInput passed: " + zdt);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    static void testParseInvalidInput() {
        String invalidInput = "Invalid Date String";
        try {
            ZonedDateTime.parse(invalidInput);
            throw new AssertionError("Expected DateTimeParseException was not thrown for invalid input");
        } catch (DateTimeParseException e) {
            System.out.println("testParseInvalidInput passed: Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
