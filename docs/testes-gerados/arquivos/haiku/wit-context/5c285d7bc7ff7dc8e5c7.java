/*
 * @test
 * @summary Verifies DateTimeFormatter.ofLocalizedDateTime null handling and basic observable behavior
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullStyleThrowsNpe();
        testNonNullStylesReturnFormatter();
    }

    static void testNullStyleThrowsNpe() {
        boolean thrown = false;
        try {
            DateTimeFormatter.ofLocalizedDateTime(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null dateTimeStyle");
        }
    }

    static void testNonNullStylesReturnFormatter() {
        FormatStyle[] styles = FormatStyle.values();
        LocalDateTime dt = LocalDateTime.of(2000, 1, 2, 3, 4, 5);
        for (FormatStyle style : styles) {
            DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(style);
            if (f == null) {
                throw new RuntimeException("Formatter must not be null for style " + style);
            }
            String s = f.format(dt);
            if (s == null || s.isEmpty()) {
                throw new RuntimeException("Formatted output must not be empty for style " + style);
            }
        }
    }
}
