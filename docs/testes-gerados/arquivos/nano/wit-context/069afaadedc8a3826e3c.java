/*
 * @test
 * @summary Tests for java.time.Year class methods
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearWitupTest {

    public static void main(String[] args) {
        testFormatWithNullFormatter();
    }

    public static void testFormatWithNullFormatter() {
        Year year = Year.of(2023);
        try {
            year.format(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
