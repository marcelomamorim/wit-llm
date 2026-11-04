/* @test    @summary Test for MonthDay.parse(CharSequence, DateTimeFormatter) handling null inputs and normal parsing    @run main MonthDayWitupTest */

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testParseNullFormatter();
        testParseNullText();
        testParseValidInput();
    }

    private static void testParseNullFormatter() {
        try {
            MonthDay.parse("--12-25", null);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        }
    }

    private static void testParseNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("--MM-dd");
        try {
            MonthDay.parse(null, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        }
    }

    private static void testParseValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("--MM-dd");
        try {
            MonthDay md = MonthDay.parse("--12-25", formatter);
            if (md.getMonthValue() == 12 && md.getDayOfMonth() == 25) {
                System.out.println("PASS: Successfully parsed valid input");
            } else {
                System.out.println("FAIL: Parsed MonthDay does not match expected values");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
