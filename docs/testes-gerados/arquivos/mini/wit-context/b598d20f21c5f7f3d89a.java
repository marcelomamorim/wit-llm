/*
 * @test
 * @summary Test java.time.LocalTime.parse(CharSequence, DateTimeFormatter) for null argument handling and normal parsing
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
    }

    private static void testParseWithNullFormatter() {
        try {
            LocalTime.parse("12:34", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        try {
            LocalTime.parse(null, formatter);
            throw new AssertionError("Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        LocalTime t = LocalTime.parse("10:15:30", formatter);
        if (t.getHour() != 10 || t.getMinute() != 15 || t.getSecond() != 30) {
            throw new AssertionError("Parsed time does not match expected values: " + t);
        }
        // Test parsing with nanoseconds
        LocalTime t2 = LocalTime.parse("10:15:30.123456789", formatter);
        if (t2.getHour() != 10 || t2.getMinute() != 15 || t2.getSecond() != 30 || t2.getNano() != 123456789) {
            throw new AssertionError("Parsed time with nanos does not match expected values: " + t2);
        }
    }
}
