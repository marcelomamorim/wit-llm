/*
 * @test
 * @summary Test java.time.LocalTime.parse(CharSequence, DateTimeFormatter) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testParseValidTime();
        testParseNullFormatter();
        testParseInvalidText();
    }

    private static void testParseValidTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        String timeStr = "10:15:30";
        LocalTime time = LocalTime.parse(timeStr, formatter);
        if (time.getHour() != 10 || time.getMinute() != 15 || time.getSecond() != 30) {
            throw new AssertionError("Parsed time does not match expected values");
        }
    }

    private static void testParseNullFormatter() {
        try {
            LocalTime.parse("10:15:30", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testParseInvalidText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        String invalidTime = "invalid-time";
        try {
            LocalTime.parse(invalidTime, formatter);
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException dtpe) {
            // expected
        }
    }
}
