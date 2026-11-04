/*
 * @test
 * @summary Verifies DateTimeFormatter.ofLocalizedDate for every FormatStyle, its ISO chronology and SMART resolver, round-trip parsing, and NullPointerException for a null style
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2020, 1, 15);

        for (FormatStyle style : FormatStyle.values()) {
            DateTimeFormatter f = DateTimeFormatter.ofLocalizedDate(style);

            check(f.getChronology() == IsoChronology.INSTANCE,
                  "chronology must be ISO for " + style);
            check(f.getResolverStyle() == ResolverStyle.SMART,
                  "resolver must be SMART for " + style);

            DateTimeFormatter us = f.withLocale(Locale.US);
            String text = us.format(date);
            check(text != null && !text.isEmpty(),
                  "formatted text must not be empty for " + style);

            LocalDate back = LocalDate.parse(text, us);
            check(date.equals(back),
                  "round-trip failed for " + style + ": " + text);
        }

        boolean npe = false;
        try {
            DateTimeFormatter.ofLocalizedDate(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null FormatStyle must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
