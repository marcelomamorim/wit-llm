/*
 * @test
 * @summary Test java.time.Year.format(DateTimeFormatter) method
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class YearWitupTest {
    public static void main(String[] args) {
        testFormatWithNullFormatter();
        testFormatWithValidFormatter();
    }

    private static void testFormatWithNullFormatter() {
        Year year = Year.of(2020);
        try {
            year.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testFormatWithValidFormatter() {
        Year year = Year.of(2020);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String result = year.format(formatter);
        if (!"2020".equals(result)) {
            throw new AssertionError("Expected formatted year to be '2020' but was '" + result + "'");
        }
    }
}
