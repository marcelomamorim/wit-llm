/* @test    @summary Test Byte.decode(String) for valid and invalid inputs    @run main ByteWitupTest */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testDecodeValidInputs();
        testDecodeInvalidInputs();
    }

    private static void testDecodeValidInputs() {
        String[] validInputs = {"127", "-128", "0", "+42", "-0"};
        for (String input : validInputs) {
            Byte result = Byte.decode(input);
            byte expected = Byte.parseByte(input.trim().replace("+", ""));
            assert result.byteValue() == expected : "Failed for input: " + input;
        }
    }

    private static void testDecodeInvalidInputs() {
        String[] invalidInputs = {"128", "-129", "abc", "", "+128", "-129"};
        for (String input : invalidInputs) {
            try {
                Byte.decode(input);
                assert false : "Expected NumberFormatException for input: " + input;
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
