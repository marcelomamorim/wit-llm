/* @test    @summary Test parsing valid short strings and invalid inputs for Short.parseShort()    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidShortStrings();
        testNullInput();
        testInvalidNumberFormat();
    }

    private static void testValidShortStrings() {
        String[] validStrings = {"0", "-32768", "32767", "12345", "-12345"};
        for (String s : validStrings) {
            try {
                short result = Short.parseShort(s);
                System.out.println("parseShort(\"" + s + "\") = " + result);
            } catch (NumberFormatException e) {
                System.err.println("Unexpected NumberFormatException for input: " + s);
                e.printStackTrace();
            }
        }
    }

    private static void testNullInput() {
        try {
            Short.parseShort(null);
            System.err.println("Expected NumberFormatException for null input, but none was thrown.");
        } catch (NumberFormatException e) {
            System.out.println("Correctly caught NumberFormatException for null input.");
        }
    }

    private static void testInvalidNumberFormat() {
        String[] invalidStrings = {"", "abc", "32768", "-32769", "12.34", "+"};
        for (String s : invalidStrings) {
            try {
                Short.parseShort(s);
                System.err.println("Expected NumberFormatException for input: \"" + s + "\", but none was thrown.");
            } catch (NumberFormatException e) {
                System.out.println("Correctly caught NumberFormatException for input: \"" + s + "\"");
            }
        }
    }
}
