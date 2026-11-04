/*
 * @test
 * @summary Test java.time.LocalTime.parse(CharSequence) method
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInputThrowsNPE();
    }

    private static void testValidParse() {
        // Valid time string
        LocalTime t = LocalTime.parse("10:15:30");
        if (t.getHour() != 10 || t.getMinute() != 15 || t.getSecond() != 30) {
            throw new AssertionError("Parsed time does not match expected values");
        }
        // Valid time string with nanos
        LocalTime t2 = LocalTime.parse("10:15:30.123456789");
        if (t2.getNano() != 123456789) {
            throw new AssertionError("Parsed nanos do not match expected value");
        }
    }

    private static void testNullInputThrowsNPE() {
        try {
            LocalTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
