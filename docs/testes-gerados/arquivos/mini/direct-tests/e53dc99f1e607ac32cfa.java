/*
 * @test
 * @summary Test java.time.LocalDateTime.parse(CharSequence) with valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    private static void testParseValid() {
        // Valid ISO_LOCAL_DATE_TIME string
        String valid = "2018-09-01T12:30:40";
        LocalDateTime ldt = LocalDateTime.parse(valid);
        if (ldt.getYear() != 2018 || ldt.getMonthValue() != 9 || ldt.getDayOfMonth() != 1
                || ldt.getHour() != 12 || ldt.getMinute() != 30 || ldt.getSecond() != 40) {
            throw new AssertionError("Parsed LocalDateTime does not match expected values: " + ldt);
        }

        // Valid ISO_LOCAL_DATE_TIME with nanoseconds
        String validNano = "2018-09-01T12:30:40.123456789";
        LocalDateTime ldtNano = LocalDateTime.parse(validNano);
        if (ldtNano.getYear() != 2018 || ldtNano.getMonthValue() != 9 || ldtNano.getDayOfMonth() != 1
                || ldtNano.getHour() != 12 || ldtNano.getMinute() != 30 || ldtNano.getSecond() != 40
                || ldtNano.getNano() != 123456789) {
            throw new AssertionError("Parsed LocalDateTime with nanos does not match expected values: " + ldtNano);
        }
    }

    private static void testParseInvalid() {
        // Null input should throw NullPointerException
        try {
            LocalDateTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }

        // Invalid format string should throw DateTimeParseException
        String invalid = "2018-09-01 12:30:40"; // space instead of 'T'
        try {
            LocalDateTime.parse(invalid);
            throw new AssertionError("Expected DateTimeParseException for invalid format");
        } catch (DateTimeParseException expected) {
        }

        // Completely invalid string
        String garbage = "not-a-date-time";
        try {
            LocalDateTime.parse(garbage);
            throw new AssertionError("Expected DateTimeParseException for garbage input");
        } catch (DateTimeParseException expected) {
        }
    }
}
