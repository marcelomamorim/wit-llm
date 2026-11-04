/*
 * @test
 * @summary Round-trip and invalid-input checks for DateTimeFormatterBuilder.appendLocalized, which reaches the LocalizedPrinterParser.parse method
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testLocalizedShortDateRoundTrip();
        testLocalizedMediumDateRoundTrip();
        testLocalizedParseRejectsInvalidText();
    }

    static void testLocalizedShortDateRoundTrip() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.SHORT, null)
                .toFormatter(Locale.US);
        LocalDate date = LocalDate.of(2020, 1, 15);
        String text = f.format(date);
        LocalDate parsed = LocalDate.parse(text, f);
        if (!date.equals(parsed)) {
            throw new RuntimeException("Round-trip failed for SHORT: " + text + " -> " + parsed);
        }
    }

    static void testLocalizedMediumDateRoundTrip() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.MEDIUM, null)
                .toFormatter(Locale.US);
        LocalDate date = LocalDate.of(1999, 12, 31);
        String text = f.format(date);
        LocalDate parsed = LocalDate.parse(text, f);
        if (!date.equals(parsed)) {
            throw new RuntimeException("Round-trip failed for MEDIUM: " + text + " -> " + parsed);
        }
    }

    static void testLocalizedParseRejectsInvalidText() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.SHORT, null)
                .toFormatter(Locale.US);
        try {
            LocalDate.parse("not-a-date", f);
            throw new RuntimeException("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }
}
