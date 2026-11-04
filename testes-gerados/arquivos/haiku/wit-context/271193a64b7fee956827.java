/*
 * @test
 * @summary Round-trip and invalid-input checks for localized formatters, which exercise DateTimeFormatterBuilder LocalizedPrinterParser.parse through public API
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testLocalizedDateRoundTrip();
        testLocalizedDateTimeRoundTrip();
        testLocalizedDateInvalidTextThrows();
        testLocalizedDateTimeInvalidTextThrows();
    }

    static void testLocalizedDateRoundTrip() {
        LocalDate date = LocalDate.of(2020, 1, 2);
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.US);
        String text = f.format(date);
        LocalDate parsed = LocalDate.parse(text, f);
        if (!date.equals(parsed)) {
            throw new RuntimeException("Round trip failed for " + text + ": " + parsed);
        }
    }

    static void testLocalizedDateTimeRoundTrip() {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 2, 16, 5);
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
                .withLocale(Locale.US);
        String text = f.format(dt);
        LocalDateTime parsed = LocalDateTime.parse(text, f);
        if (!dt.equals(parsed)) {
            throw new RuntimeException("Round trip failed for " + text + ": " + parsed);
        }
    }

    static void testLocalizedDateInvalidTextThrows() {
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.US);
        try {
            LocalDate.parse("not a date", f);
            throw new RuntimeException("Expected DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testLocalizedDateTimeInvalidTextThrows() {
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
                .withLocale(Locale.US);
        try {
            LocalDateTime.parse("zzz", f);
            throw new RuntimeException("Expected DateTimeParseException");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
