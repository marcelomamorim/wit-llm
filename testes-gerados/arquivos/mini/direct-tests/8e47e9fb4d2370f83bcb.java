/*
 * @test
 * @summary Test java.time.OffsetTime.format(DateTimeFormatter) method
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        OffsetTime time = OffsetTime.of(10, 20, 30, 400_000_000, ZoneOffset.ofHours(2));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        String formatted = time.format(formatter);
        if (formatted == null || formatted.isEmpty()) {
            throw new AssertionError("Formatted string should not be null or empty");
        }
        // Basic check: formatted string contains hour and offset
        if (!formatted.contains("10") || !formatted.contains("+02:00")) {
            throw new AssertionError("Formatted string does not contain expected hour or offset: " + formatted);
        }
    }

    private static void testFormatWithNullFormatter() {
        OffsetTime time = OffsetTime.of(0, 0, 0, 0, ZoneOffset.UTC);
        try {
            time.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
