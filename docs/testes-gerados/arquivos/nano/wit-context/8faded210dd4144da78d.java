/*
 * @test
 * @summary Tests for java.time.LocalDateTime.parse(CharSequence, DateTimeFormatter)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    public static void testParseWithNullFormatter() {
        CharSequence text = "2023-03-25T10:15:30";
        DateTimeFormatter formatter = null;
        try {
            LocalDateTime.parse(text, formatter);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        }
    }

    public static void testParseWithNullText() {
        CharSequence text = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            LocalDateTime.parse(text, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        }
    }
}
