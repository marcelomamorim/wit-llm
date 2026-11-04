/*
 * @test
 * @summary Test java.time.ZonedDateTime.format(DateTimeFormatter) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatReturnsExpectedString();
        testFormatWithNullFormatterThrowsNPE();
        testFormatWithFormatterThrowingExceptionPropagates();
    }

    private static void testFormatReturnsExpectedString() {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 30, 45), ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String formatted = zdt.format(formatter);
        // The formatted string should contain the date, time and zone
        if (!formatted.contains("2018-09-01T12:30:45") || !formatted.contains("Z")) {
            throw new AssertionError("Formatted string does not contain expected date/time/zone: " + formatted);
        }
    }

    private static void testFormatWithNullFormatterThrowsNPE() {
        ZonedDateTime zdt = ZonedDateTime.now();
        try {
            zdt.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testFormatWithFormatterThrowingExceptionPropagates() {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter badFormatter = new DateTimeFormatter() {
            @Override
            public String format(java.time.temporal.TemporalAccessor temporal) {
                throw new DateTimeParseException("fail", "input", 0);
            }
        };
        try {
            zdt.format(badFormatter);
            throw new AssertionError("Expected DateTimeParseException to be thrown");
        } catch (DateTimeParseException ex) {
            // expected
        }
    }
}
