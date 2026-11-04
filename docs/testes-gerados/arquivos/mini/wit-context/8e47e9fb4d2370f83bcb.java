/*
 * @test
 * @summary Test java.time.OffsetTime.format(DateTimeFormatter) method
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        // Arrange
        OffsetTime time = OffsetTime.of(LocalTime.of(10, 15, 30, 123_000_000), ZoneOffset.ofHours(2));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;

        // Act
        String result = time.format(formatter);

        // Assert
        if (result == null || result.isEmpty()) {
            throw new AssertionError("format() returned null or empty string");
        }
        // Basic check: result should contain hour and offset
        if (!result.contains("10:15:30") || !result.contains("+02:00")) {
            throw new AssertionError("format() output unexpected: " + result);
        }
    }

    private static void testFormatWithNullFormatter() {
        // Arrange
        OffsetTime time = OffsetTime.of(LocalTime.of(0, 0), ZoneOffset.UTC);

        // Act & Assert
        try {
            time.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException ex) {
            // Expected exception
            if (!"formatter".equals(ex.getMessage())) {
                throw new AssertionError("NullPointerException message unexpected: " + ex.getMessage());
            }
        }
    }
}
