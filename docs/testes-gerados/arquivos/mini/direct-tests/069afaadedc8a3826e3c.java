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
    public static void main(String[] args) throws Exception {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    private static void testFormatWithValidFormatter() {
        Year year = Year.of(2020);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String result = year.format(formatter);
        if (!"2020".equals(result)) {
            throw new AssertionError("Expected '2020' but got '" + result + "'");
        }
    }

    private static void testFormatWithNullFormatter() {
        Year year = Year.of(2020);
        try {
            year.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // expected
            if (!"formatter".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
