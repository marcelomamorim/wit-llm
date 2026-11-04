/*
 * @test
 * @summary Test java.time.ZonedDateTime.format(DateTimeFormatter) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        ZonedDateTime zdt = ZonedDateTime.of(
            LocalDateTime.of(2018, 9, 1, 12, 30, 45, 123000000),
            ZoneId.of("UTC")
        );
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        String result = zdt.format(formatter);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("format returned null or empty string");
        }
        // Basic sanity check: result should contain year and zone
        if (!result.contains("2018") || !result.contains("Z")) {
            throw new AssertionError("format output unexpected: " + result);
        }
    }

    private static void testFormatWithNullFormatter() {
        ZonedDateTime zdt = ZonedDateTime.of(
            LocalDateTime.of(2018, 9, 1, 12, 30, 45, 123000000),
            ZoneId.of("UTC")
        );
        try {
            zdt.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
