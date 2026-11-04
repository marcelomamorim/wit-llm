/*
 * @test
 * @summary Test java.time.LocalTime.format(DateTimeFormatter) method
 * @run main LocalTimeWitupTest
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
        LocalTime time = LocalTime.of(13, 45, 20, 123456789);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
        String result = time.format(formatter);
        // The result should be a non-null string containing the time formatted in ISO_LOCAL_TIME
        if (result == null || !result.startsWith("13:45:20")) {
            throw new AssertionError("Unexpected format result: " + result);
        }
    }

    private static void testFormatWithNullFormatter() {
        LocalTime time = LocalTime.of(0, 0);
        try {
            time.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"formatter".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        }
    }
}
