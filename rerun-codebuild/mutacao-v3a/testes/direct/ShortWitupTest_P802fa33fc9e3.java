/* @test    @summary Test Short.decode for valid and invalid inputs    @run main ShortWitupTest_P802fa33fc9e3 */

import java.lang.Short;

public class ShortWitupTest_P802fa33fc9e3 {

    public static void main(String[] args) {
        testDecodeValidInput("12345");
        testDecodeValidInput("-12345");
        testDecodeValidInput("0");
        testDecodeValidInput("+32767");
        testDecodeValidInput("-32768");
        testDecodeInvalidInput("abc");
        testDecodeInvalidInput("123abc");
        testDecodeInvalidInput("");
        testDecodeInvalidInput(null);
        testDecodeOutOfRange("-32769");
        testDecodeOutOfRange("32768");
    }

    private static void testDecodeValidInput(String input) {
        try {
            Short result = Short.decode(input);
            System.out.println("decode(\"" + input + "\") = " + result);
        } catch (NumberFormatException e) {
            System.err.println("Unexpected exception for input: " + input);
        }
    }

    private static void testDecodeInvalidInput(String input) {
        try {
            Short.decode(input);
            System.err.println("Expected exception not thrown for input: " + input);
        } catch (NumberFormatException e) {
            System.out.println("Correctly threw exception for input: " + input);
        }
    }

    private static void testDecodeOutOfRange(String input) {
        try {
            Short.decode(input);
            System.err.println("Expected out of range exception not thrown for input: " + input);
        } catch (NumberFormatException e) {
            System.out.println("Correctly threw out of range exception for input: " + input);
        }
    }
}
