/*
 * @test
 * @summary Test OffsetTime.parse(CharSequence) for null input and normal parsing
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testParseNullInput();
        testParseValidString();
        testParseInvalidString();
    }

    private static void testParseNullInput() {
        // According to source, parse(null) should throw NullPointerException
        try {
            OffsetTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testParseValidString() {
        // Valid ISO_OFFSET_TIME string with offset
        String valid = "10:15:30+01:00";
        OffsetTime ot = OffsetTime.parse(valid);
        if (ot == null) {
            throw new AssertionError("Parsed OffsetTime should not be null");
        }
        if (ot.getHour() != 10 || ot.getMinute() != 15 || ot.getSecond() != 30) {
            throw new AssertionError("Parsed time fields incorrect: " + ot);
        }
        if (!"+01:00".equals(ot.getOffset().toString())) {
            throw new AssertionError("Parsed offset incorrect: " + ot.getOffset());
        }
    }

    private static void testParseInvalidString() {
        // Invalid string should throw DateTimeParseException
        String invalid = "invalid-time-string";
        try {
            OffsetTime.parse(invalid);
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
