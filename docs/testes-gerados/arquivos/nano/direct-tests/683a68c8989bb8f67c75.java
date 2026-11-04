/*
 * @test
 * @summary Test ZonedDateTime.format with various formatters
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testFormatWithISO_LOCAL_DATE_TIME();
        testFormatWithISO_ZONED_DATE_TIME();
        testFormatWithCustomFormatter();
    }

    static void testFormatWithISO_LOCAL_DATE_TIME() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 45, 0, ZoneId.of("UTC"));
        String result = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String expected = "2023-03-15T10:30:45";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    static void testFormatWithISO_ZONED_DATE_TIME() {
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 45, 0, ZoneId.of("America/New_York"));
        String result = zdt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        String expectedPrefix = "2023-03-15T10:30:45";
        if (!result.startsWith(expectedPrefix)) {
            throw new AssertionError("Result does not start with expected prefix: " + expectedPrefix + ", but got: " + result);
        }
        if (!result.contains("-05:00")) {
            throw new AssertionError("Expected timezone offset -05:00 in result, but got: " + result);
        }
    }

    static void testFormatWithCustomFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss z");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 45, 0, ZoneId.of("Europe/Paris"));
        String result = zdt.format(formatter);
        String expected = "2023/03/15 10:30:45 CET";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }
}
