/*
 * @test
 * @summary Tests for java.time.Year.parse(CharSequence, DateTimeFormatter)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class YearWitupTest {

    public static void main(String[] args) {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
        testParseWithInvalidInput();
    }

    public static void testParseWithNullFormatter() {
        try {
            Year.parse("2023", null);
            System.out.println("FAIL: Expected NullPointerException for null formatter");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null formatter");
        }
    }

    public static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        try {
            Year.parse(null, formatter);
            System.out.println("FAIL: Expected NullPointerException for null text");
        } catch (NullPointerException e) {
            System.out.println("PASS: Caught expected NullPointerException for null text");
        }
    }

    public static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        try {
            Year y = Year.parse("2023", formatter);
            if (y.getValue() == 2023) {
                System.out.println("PASS: Parsed year 2023 successfully");
            } else {
                System.out.println("FAIL: Parsed year does not match expected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testParseWithInvalidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        try {
            Year.parse("notayear", formatter);
            System.out.println("FAIL: Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            System.out.println("PASS: Caught expected DateTimeParseException for invalid input");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
