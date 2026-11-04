/* @test    @summary Test for Byte.parseByte(String s) method to verify exception paths and behavior.    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testParseByteNull();
        testParseByteInvalidFormat();
        testParseByteValid();
    }

    private static void testParseByteNull() {
        try {
            Byte.parseByte(null);
            System.err.println("Failed: Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            System.out.println("Passed: Null input throws NumberFormatException");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    private static void testParseByteInvalidFormat() {
        String[] invalidInputs = {"", "abc", "128", "-129", "10", "36"};
        for (String input : invalidInputs) {
            try {
                Byte.parseByte(input);
                System.err.println("Failed: Expected NumberFormatException for input: '" + input + "'");
            } catch (NumberFormatException e) {
                System.out.println("Passed: Input '" + input + "' throws NumberFormatException");
            } catch (Exception e) {
                System.err.println("Failed: Unexpected exception for input '" + input + "': " + e);
            }
        }
    }

    private static void testParseByteValid() {
        String[] validInputs = {"0", "1", "127", "-128", "36", "-36"};
        for (String input : validInputs) {
            try {
                byte result = Byte.parseByte(input);
                System.out.println("Parsed '" + input + "' as byte: " + result);
            } catch (Exception e) {
                System.err.println("Failed: Unexpected exception for valid input '" + input + "': " + e);
            }
        }
    }
}
