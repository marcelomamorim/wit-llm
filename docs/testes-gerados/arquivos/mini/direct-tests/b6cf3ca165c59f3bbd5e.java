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
    public static void main(String[] args) throws Exception {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.of(2018, 9, 1, 12, 30, 45, 123000000), ZoneOffset.ofHours(-3));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String formatted = odt.format(formatter);
        // The formatted string must contain the date, time and offset
        if (!formatted.contains("2018-09-01T12:30:45") || !formatted.contains("-03:00")) {
            throw new AssertionError("Formatted string does not contain expected date/time/offset: " + formatted);
        }
    }

    private static void testFormatWithNullFormatter() {
        OffsetDateTime odt = OffsetDateTime.now();
        try {
            odt.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
