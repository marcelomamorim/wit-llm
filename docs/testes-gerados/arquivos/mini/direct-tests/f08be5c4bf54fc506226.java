/*
 * @test
 * @summary Test java.time.LocalDate.parse(CharSequence) with ISO_LOCAL_DATE format
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testParseValidDate();
        testParseInvalidDate();
        testParseEmptyString();
        testParseNull();
    }

    private static void testParseValidDate() {
        LocalDate date = LocalDate.parse("2018-09-01");
        if (date.getYear() != 2018 || date.getMonthValue() != 9 || date.getDayOfMonth() != 1) {
            throw new AssertionError("Parsed date does not match expected values");
        }
    }

    private static void testParseInvalidDate() {
        try {
            LocalDate.parse("2018-13-01"); // Invalid month
            throw new AssertionError("Expected DateTimeParseException for invalid month");
        } catch (DateTimeParseException expected) {
            // Expected exception
        }

        try {
            LocalDate.parse("2018-02-30"); // Invalid day
            throw new AssertionError("Expected DateTimeParseException for invalid day");
        } catch (DateTimeParseException expected) {
            // Expected exception
        }

        try {
            LocalDate.parse("not-a-date"); // Not a date
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException expected) {
            // Expected exception
        }
    }

    private static void testParseEmptyString() {
        try {
            LocalDate.parse("");
            throw new AssertionError("Expected DateTimeParseException for empty string");
        } catch (DateTimeParseException expected) {
            // Expected exception
        }
    }

    private static void testParseNull() {
        try {
            LocalDate.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
