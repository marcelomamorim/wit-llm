/* @test    @summary Test for OffsetDateTime.parse(CharSequence, DateTimeFormatter) method.    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    private static void testParseWithNullFormatter() {
        CharSequence text = "2023-03-25T10:15:30+01:00";
        DateTimeFormatter formatter = null;
        try {
            OffsetDateTime.parse(text, formatter);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        }
    }

    private static void testParseWithNullText() {
        CharSequence text = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            OffsetDateTime.parse(text, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        }
    }
}
