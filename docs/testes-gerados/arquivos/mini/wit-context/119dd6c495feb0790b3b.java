/*
 * @test
 * @summary Test java.time.LocalDate.format(DateTimeFormatter) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        LocalDate date = LocalDate.of(2018, Month.SEPTEMBER, 25);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String formatted = date.format(formatter);
        if (!"2018-09-25".equals(formatted)) {
            throw new AssertionError("Expected formatted date to be '2018-09-25' but was '" + formatted + "'");
        }
    }

    private static void testFormatWithNullFormatter() {
        LocalDate date = LocalDate.of(2018, 9, 25);
        try {
            date.format(null);
            throw new AssertionError("Expected NullPointerException when formatter is null");
        } catch (NullPointerException e) {
            // Expected exception
            if (!"formatter".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + e.getMessage());
            }
        }
    }
}
