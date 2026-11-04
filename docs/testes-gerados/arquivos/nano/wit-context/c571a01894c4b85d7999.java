/* @test    @summary Test for Byte(String s) constructor handling null and invalid inputs    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testValidInput();
        testInvalidInput();
    }

    private static void testNullInput() {
        try {
            new Byte((String) null);
            System.out.println("FAIL: Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            System.out.println("PASS: Caught expected NumberFormatException for null input");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null input: " + e);
        }
    }

    private static void testValidInput() {
        try {
            Byte b = new Byte("127");
            if (b != null && b.byteValue() == 127) {
                System.out.println("PASS: Valid input '127' parsed correctly");
            } else {
                System.out.println("FAIL: Valid input '127' did not parse correctly");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid input: " + e);
        }
    }

    private static void testInvalidInput() {
        String[] invalidInputs = {"abc", "", "128", "-129", "12.3", "  "};
        for (String input : invalidInputs) {
            try {
                new Byte(input);
                System.out.println("FAIL: Expected NumberFormatException for input: '" + input + "'");
            } catch (NumberFormatException e) {
                System.out.println("PASS: Caught expected NumberFormatException for input: '" + input + "'");
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception for input: '" + input + "': " + e);
            }
        }
    }
}
