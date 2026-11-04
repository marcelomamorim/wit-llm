/*
 * @test
 * @summary Test the format method of LocalTime with various formatters.
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithDefaultFormatter();
        testFormatWithCustomFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithDefaultFormatter() {
        LocalTime time = LocalTime.of(12, 30, 45, 123456789);
        String result = time.format(DateTimeFormatter.ISO_LOCAL_TIME);
        System.out.println("Default ISO_LOCAL_TIME format: " + result);
        // Expected output: 12:30:45.123456789
    }

    static void testFormatWithCustomFormatter() {
        DateTimeFormatter customFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.HOUR_OF_DAY, 2, 2, SignStyle.NOT_NEGATIVE)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendLiteral('.')
            .appendValue(ChronoField.NANO_OF_SECOND, 9)
            .toFormatter();
        LocalTime time = LocalTime.of(9, 5, 3, 987654321);
        String result = time.format(customFormatter);
        System.out.println("Custom formatter output: " + result);
        // Expected output: 09:05:03.987654321
    }

    static void testFormatWithNullFormatter() {
        try {
            LocalTime time = LocalTime.of(10, 10);
            time.format(null);
            System.out.println("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException when formatter is null.");
        }
    }
}
