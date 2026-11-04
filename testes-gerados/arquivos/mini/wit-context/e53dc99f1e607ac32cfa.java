/*
 * @test
 * @summary Test LocalDateTime.parse(CharSequence) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInputThrowsNPE();
    }

    private static void testValidParse() {
        String validDateTime = "2018-09-01T12:30:40";
        LocalDateTime ldt = LocalDateTime.parse(validDateTime);
        if (ldt.getYear() != 2018 || ldt.getMonthValue() != 9 || ldt.getDayOfMonth() != 1
                || ldt.getHour() != 12 || ldt.getMinute() != 30 || ldt.getSecond() != 40) {
            throw new AssertionError("Parsed LocalDateTime does not match expected values: " + ldt);
        }
    }

    private static void testNullInputThrowsNPE() {
        try {
            LocalDateTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
