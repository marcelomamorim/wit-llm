/*
 * @test
 * @summary Test java.time.LocalDateTime.format(DateTimeFormatter) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatReturnsExpectedString();
        testFormatWithNullFormatterThrowsNPE();
        testFormatWithFormatterThatThrowsException();
    }

    private static void testFormatReturnsExpectedString() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 25, 13, 45, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formatted = dt.format(formatter);
        // Expected format: 2018-09-25T13:45:30
        if (!"2018-09-25T13:45:30".equals(formatted)) {
            throw new AssertionError("Expected '2018-09-25T13:45:30' but got '" + formatted + "'");
        }
    }

    private static void testFormatWithNullFormatterThrowsNPE() {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 25, 13, 45);
        try {
            dt.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // Expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }

    private static void testFormatWithFormatterThatThrowsException() {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 25, 13, 45);
        DateTimeFormatter badFormatter = new DateTimeFormatter() {
            @Override
            public String format(java.time.temporal.TemporalAccessor temporal) {
                throw new DateTimeParseException("Forced failure", "", 0);
            }
        };
        try {
            dt.format(badFormatter);
            throw new AssertionError("Expected DateTimeParseException from formatter");
        } catch (DateTimeParseException ex) {
            // Expected
        }
    }
}