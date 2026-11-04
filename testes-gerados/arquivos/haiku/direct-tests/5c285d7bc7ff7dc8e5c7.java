/*
 * @test
 * @summary Verifies DateTimeFormatter.ofLocalizedDateTime rejects null and produces a usable localized formatter
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullStyleThrowsNullPointerException();
        testNonNullStyleReturnsFormatterThatUsesYear();
    }

    static void testNullStyleThrowsNullPointerException() {
        try {
            DateTimeFormatter.ofLocalizedDateTime(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null FormatStyle");
    }

    static void testNonNullStyleReturnsFormatterThatUsesYear() {
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.US);
        if (f == null) {
            throw new RuntimeException("Formatter must not be null");
        }
        String out2020 = f.format(LocalDateTime.of(2020, 1, 2, 15, 4, 5));
        String out2021 = f.format(LocalDateTime.of(2021, 1, 2, 15, 4, 5));
        if (out2020 == null || out2020.isEmpty()) {
            throw new RuntimeException("Formatted output must not be empty");
        }
        if (!out2020.contains("2020")) {
            throw new RuntimeException("Expected year 2020 in output: " + out2020);
        }
        if (!out2021.contains("2021")) {
            throw new RuntimeException("Expected year 2021 in output: " + out2021);
        }
    }
}
