/*
 * @test
 * @summary Verify toFormatter(Locale) rejects a null locale and applies the given locale to the built formatter
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNpe();
        testLocaleIsApplied();
    }

    static void testNullLocaleThrowsNpe() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR)
                    .toFormatter((Locale) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("toFormatter(null) did not throw NullPointerException");
        }
    }

    static void testLocaleIsApplied() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .toFormatter(Locale.US);
        if (!Locale.US.equals(f.getLocale())) {
            throw new RuntimeException("Unexpected locale: " + f.getLocale());
        }
        String s = f.format(LocalDate.of(2020, 1, 2));
        if (!"2020".equals(s)) {
            throw new RuntimeException("Unexpected format result: " + s);
        }
    }
}
