/*
 * @test
 * @summary Tests for java.time.format.DateTimeFormatter.parseObject(String)
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.text.ParseException;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testParseObjectWithNullInput();
        testParseObjectWithValidString();
        testParseObjectWithInvalidString();
    }

    public static void testParseObjectWithNullInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parseObject(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testParseObjectWithValidString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            Object result = formatter.parseObject("2023-03-15");
            if (result == null) {
                System.err.println("parseObject returned null for valid input");
                System.exit(1);
            }
        } catch (ParseException e) {
            System.err.println("Unexpected ParseException for valid input: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void testParseObjectWithInvalidString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            formatter.parseObject("invalid-date");
            System.err.println("Expected ParseException not thrown");
            System.exit(1);
        } catch (ParseException e) {
            // Expected exception
        }
    }
}
