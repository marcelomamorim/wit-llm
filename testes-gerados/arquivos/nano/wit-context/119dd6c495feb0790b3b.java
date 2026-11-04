/*
 * @test
 * @summary Test for LocalDate.format method, including null formatter case
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testFormatWithValidFormatter();
        testFormatWithNullFormatter();
    }

    static void testFormatWithValidFormatter() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String result = date.format(formatter);
        if (!"2023-03-15".equals(result)) {
            throw new AssertionError("Expected '2023-03-15' but got: " + result);
        }
    }

    static void testFormatWithNullFormatter() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        try {
            date.format(null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
