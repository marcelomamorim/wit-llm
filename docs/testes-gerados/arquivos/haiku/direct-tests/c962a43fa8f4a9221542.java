/*
 * @test
 * @summary Verifies appendLocalized argument validation, fluent return, and localized formatting output
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testBothNullThrows();
        testReturnsSameBuilder();
        testDateAndTimeFormatsContainYear();
        testDateOnlyFormatsContainYear();
    }

    static void testBothNullThrows() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            builder.appendLocalized(null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException when both styles are null");
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendLocalized(FormatStyle.SHORT, null);
        if (result != builder) {
            throw new AssertionError("appendLocalized must return this builder");
        }
        result = builder.appendLocalized(null, FormatStyle.SHORT);
        if (result != builder) {
            throw new AssertionError("appendLocalized must return this builder for time-only style");
        }
    }

    static void testDateAndTimeFormatsContainYear() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.MEDIUM, FormatStyle.SHORT)
                .toFormatter(Locale.US);
        String text = formatter.format(LocalDateTime.of(2018, 9, 1, 10, 30));
        if (text == null || text.isEmpty()) {
            throw new AssertionError("Formatted text must not be empty");
        }
        if (!text.contains("2018")) {
            throw new AssertionError("Expected year in output but was: " + text);
        }
    }

    static void testDateOnlyFormatsContainYear() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendLocalized(FormatStyle.LONG, null)
                .toFormatter(Locale.US);
        String text = formatter.format(LocalDate.of(2018, 9, 1));
        if (!text.contains("2018")) {
            throw new AssertionError("Expected year in date-only output but was: " + text);
        }
    }
}
