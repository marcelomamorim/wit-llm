/*
 * @test
 * @summary Test java.time.OffsetDateTime.format(DateTimeFormatter) method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        // Arrange
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 6, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // Act
        String result = odt.format(formatter);

        // Assert
        if (result == null || result.isEmpty()) {
            throw new AssertionError("format returned null or empty string");
        }
        // Basic check: result should contain date and offset
        if (!result.contains("2020-01-02T03:04:05") || !result.contains("Z")) {
            throw new AssertionError("format output unexpected: " + result);
        }
    }

    private static void testFormatWithNullFormatter() {
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0), ZoneOffset.UTC);
        try {
            odt.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message unexpected: " + npe.getMessage());
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
