/* @test    @summary Test Byte.parseByte(String s) with valid and invalid inputs    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testValidParseByte();
        testInvalidParseByte();
    }

    private static void testValidParseByte() {
        String[] validInputs = {"0", "-128", "127", "42", "-1"};
        byte[] expectedOutputs = {0, -128, 127, 42, -1};
        for (int i = 0; i < validInputs.length; i++) {
            String input = validInputs[i];
            byte expected = expectedOutputs[i];
            byte result = Byte.parseByte(input);
            if (result != expected) {
                throw new AssertionError("Failed parsing valid input: " + input + ", expected: " + expected + ", but got: " + result);
            }
        }
    }

    private static void testInvalidParseByte() {
        String[] invalidInputs = {"", "abc", "128", "-129", "12.3", null};
        for (String input : invalidInputs) {
            try {
                Byte.parseByte(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception, test passes for this input
            } catch (NullPointerException e) {
                if (input != null) {
                    throw new AssertionError("Unexpected NullPointerException for input: " + input);
                }
                // Null input should throw NullPointerException, which is acceptable
            }
        }
    }
}
