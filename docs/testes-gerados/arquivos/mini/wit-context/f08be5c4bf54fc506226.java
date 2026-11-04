/*
 * @test
 * @summary Test java.time.LocalDate.parse(CharSequence) method
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullTextThrowsNPE();
    }

    private static void testValidParse() {
        // Basic valid parse test
        LocalDate date = LocalDate.parse("2018-09-01");
        if (date.getYear() != 2018 || date.getMonthValue() != 9 || date.getDayOfMonth() != 1) {
            throw new AssertionError("Parsed date does not match expected values");
        }
    }

    private static void testNullTextThrowsNPE() {
        try {
            LocalDate.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
