/*
 * @test
 * @summary Tests the parse method of OffsetTime with a formatter.
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testParseWithValidInput();
        testParseWithNullFormatter();
        testParseWithInvalidInput();
    }

    static void testParseWithValidInput() {
        try {
            String input = "10:15:30+01:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");
            OffsetTime result = OffsetTime.parse(input, formatter);
            assert result != null : "Parsed OffsetTime should not be null";
            System.out.println("testParseWithValidInput passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseWithValidInput", e);
        }
    }

    static void testParseWithNullFormatter() {
        try {
            String input = "10:15:30+01:00";
            OffsetTime.parse(input, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testParseWithNullFormatter passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseWithNullFormatter", e);
        }
    }

    static void testParseWithInvalidInput() {
        try {
            String input = "invalid-time";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX");
            OffsetTime.parse(input, formatter);
            throw new AssertionError("Expected DateTimeParseException not thrown");
        } catch (DateTimeParseException e) {
            System.out.println("testParseWithInvalidInput passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testParseWithInvalidInput", e);
        }
    }
}
