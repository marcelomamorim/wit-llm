/*
 * @test
 * @summary Test java.time.LocalDateTime.format(DateTimeFormatter) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.SEPTEMBER, 25, 15, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String result = ldt.format(formatter);
        // The result should be a non-null string containing the date and time in ISO format
        if (result == null || !result.contains("2018-09-25T15:30")) {
            throw new AssertionError("format() did not produce expected output: " + result);
        }
    }

    private static void testFormatWithNullFormatter() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.SEPTEMBER, 25, 15, 30);
        try {
            ldt.format(null);
            throw new AssertionError("format(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"formatter".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        }
    }
}
