/*
 * @test
 * @summary Test LocalTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testParseWithValidInputAndFormatter();
        testParseWithInvalidInput();
        testParseWithNullFormatter();
    }

    static void testParseWithValidInputAndFormatter() {
        String timeString = "10:15:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime result = LocalTime.parse(timeString, formatter);
        assert result.equals(LocalTime.of(10, 15, 30)) : "Expected 10:15:30, got " + result;
    }

    static void testParseWithInvalidInput() {
        String invalidTimeString = "invalid_time";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime.parse(invalidTimeString, formatter);
            assert false : "Expected DateTimeParseException";
        } catch (DateTimeParseException e) {
            // expected
        }
    }

    static void testParseWithNullFormatter() {
        String timeString = "10:15:30";
        try {
            LocalTime.parse(timeString, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
