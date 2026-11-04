/*
 * @test
 * @summary Test OffsetTime.format with various formatters
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithISO();
        testFormatWithCustomPattern();
        testFormatWithBuilder();
    }

    static void testFormatWithISO() {
        OffsetTime time = OffsetTime.of(12, 30, 45, 123456789, OffsetTime.now().getOffset());
        String result = time.format(DateTimeFormatter.ISO_OFFSET_TIME);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("ISO_OFFSET_TIME formatter returned null or empty string");
        }
    }

    static void testFormatWithCustomPattern() {
        OffsetTime time = OffsetTime.of(8, 15, 0, 0, OffsetTime.now().getOffset());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");
        String result = time.format(formatter);
        if (result == null || !result.matches("^\d{2}:\d{2}:\d{2}[+-]\d{2}:\d{2}$")) {
            throw new AssertionError("Custom pattern formatter did not produce expected format: " + result);
        }
    }

    static void testFormatWithBuilder() {
        OffsetTime time = OffsetTime.of(23, 59, 59, 999999999, OffsetTime.ofHours(5).getOffset());
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern("HH:mm")
            .appendOffset("Z", "+00:00")
            .toFormatter();
        String result = time.format(formatter);
        if (result == null || !result.contains("+05:00")) {
            throw new AssertionError("Builder formatter did not include offset: " + result);
        }
    }
}
