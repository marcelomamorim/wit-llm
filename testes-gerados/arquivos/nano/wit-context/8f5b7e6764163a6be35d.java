/*
 * @test
 * @summary Tests for java.time.LocalTime.parse(CharSequence)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testParseValidInput();
        testParseNullInput();
        testParseNullFormatter();
    }

    private static void testParseValidInput() {
        String validTime = "13:45:30";
        LocalTime result = LocalTime.parse(validTime);
        if (result == null) {
            throw new AssertionError("Parsing valid input returned null");
        }
        if (!result.equals(LocalTime.of(13, 45, 30))) {
            throw new AssertionError("Parsed time does not match expected") ;
        }
    }

    private static void testParseNullInput() {
        try {
            LocalTime.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testParseNullFormatter() {
        String validTime = "13:45:30";
        try {
            LocalTime.parse(validTime, null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
