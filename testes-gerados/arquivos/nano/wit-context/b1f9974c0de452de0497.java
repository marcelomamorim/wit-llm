/* @test    @summary Test OffsetTime.parse(CharSequence, DateTimeFormatter) for null inputs and normal behavior    @run main OffsetTimeWitupTest */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
    }

    private static void testParseWithNullFormatter() {
        try {
            OffsetTime.parse("12:30:00+01:00", null);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null formatter: " + e);
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        try {
            OffsetTime.parse(null, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null text: " + e);
        }
    }

    private static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        try {
            OffsetTime ot = OffsetTime.parse("12:30:00+01:00", formatter);
            if (ot != null && ot.toString().equals("12:30:00+01:00")) {
                System.out.println("PASS: Successfully parsed valid input");
            } else {
                System.out.println("FAIL: Parsed OffsetTime does not match expected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during valid parse: " + e);
        }
    }
}
