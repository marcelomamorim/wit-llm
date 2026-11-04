/*
 * @test
 * @summary Test java.time.LocalTime.format(DateTimeFormatter) method
 * @run main java.time.LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        LocalTime time = LocalTime.of(13, 45, 30, 123456789);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        String formatted = time.format(formatter);
        // The formatted string should start with "13:45:30"
        if (!formatted.startsWith("13:45:30")) {
            throw new AssertionError("Expected formatted string to start with '13:45:30' but was: " + formatted);
        }
    }

    private static void testFormatWithNullFormatter() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
