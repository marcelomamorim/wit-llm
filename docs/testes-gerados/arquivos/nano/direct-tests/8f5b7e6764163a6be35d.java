/*
 * @test
 * @summary Tests for LocalTime.parse(CharSequence)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testParseValidTime();
        testParseInvalidTime();
        testParseWithCustomFormatter();
    }

    static void testParseValidTime() {
        try {
            LocalTime time = LocalTime.parse("10:15:30");
            assert time.getHour() == 10 : "Expected hour 10, got " + time.getHour();
            assert time.getMinute() == 15 : "Expected minute 15, got " + time.getMinute();
            assert time.getSecond() == 30 : "Expected second 30, got " + time.getSecond();
        } catch (Exception e) {
            throw new AssertionError("Parsing valid time failed", e);
        }
    }

    static void testParseInvalidTime() {
        try {
            LocalTime.parse("invalid-time");
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            // expected
        }
    }

    static void testParseWithCustomFormatter() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m:s");
            LocalTime time = LocalTime.parse("3:5:7", formatter);
            assert time.getHour() == 3 : "Expected hour 3, got " + time.getHour();
            assert time.getMinute() == 5 : "Expected minute 5, got " + time.getMinute();
            assert time.getSecond() == 7 : "Expected second 7, got " + time.getSecond();
        } catch (Exception e) {
            throw new AssertionError("Parsing with custom formatter failed", e);
        }
    }
}
