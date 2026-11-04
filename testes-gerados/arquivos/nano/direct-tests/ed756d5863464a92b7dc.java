/* @test    @summary Test OffsetDateTime.parse(CharSequence) method for valid ISO_OFFSET_DATE_TIME strings    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testParseValidOffsetDateTime();
        testParseInvalidOffsetDateTime();
    }

    static void testParseValidOffsetDateTime() {
        String validText = "2023-03-25T10:15:30+01:00";
        OffsetDateTime result = OffsetDateTime.parse(validText);
        if (result == null) {
            throw new AssertionError("Parsing valid OffsetDateTime returned null");
        }
        OffsetDateTime expected = OffsetDateTime.of(2023, 3, 25, 10, 15, 30, 0, java.time.ZoneOffset.ofHours(1));
        if (!result.equals(expected)) {
            throw new AssertionError("Parsed OffsetDateTime does not match expected. Got: " + result + ", expected: " + expected);
        }
    }

    static void testParseInvalidOffsetDateTime() {
        String invalidText = "2023-13-25T10:15:30+01:00"; // Invalid month
        try {
            OffsetDateTime.parse(invalidText);
            throw new AssertionError("Expected DateTimeParseException for invalid date string");
        } catch (java.time.format.DateTimeParseException e) {
            // Expected exception
        }
    }
}
