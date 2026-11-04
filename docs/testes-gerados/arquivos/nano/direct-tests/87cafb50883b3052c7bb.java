/*
 * @test
 * @summary Test the format method of LocalDateTime with a simple formatter
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithISO_LOCAL_DATE_TIME();
        testFormatWithCustomPattern();
    }

    static void testFormatWithISO_LOCAL_DATE_TIME() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String result = dateTime.format(formatter);
        String expected = "2023-03-15T10:30:45";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testFormatWithCustomPattern() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 8, 15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String result = dateTime.format(formatter);
        String expected = "25/12/2023 08:15";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
