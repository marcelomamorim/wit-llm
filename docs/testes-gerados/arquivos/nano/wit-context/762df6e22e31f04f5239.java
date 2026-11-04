/*
 * @test
 * @summary Tests for java.time.YearMonth.parse(CharSequence, DateTimeFormatter)
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
    }

    public static void testParseWithNullFormatter() {
        try {
            YearMonth.parse("2023-03", null);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null formatter: " + t);
        }
    }

    public static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM");
        try {
            YearMonth.parse(null, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null text: " + t);
        }
    }
}
