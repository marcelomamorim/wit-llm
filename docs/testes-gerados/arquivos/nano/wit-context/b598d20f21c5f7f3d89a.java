/*
 * @test
 * @summary Tests for java.time.LocalTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    private static void testParseWithNullFormatter() {
        try {
            LocalTime.parse("12:30", null);
            System.err.println("Failed: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null formatter");
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(null, formatter);
            System.err.println("Failed: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null text");
        }
    }
}
