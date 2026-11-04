/* @test    @summary Test java.lang.Short.parseShort(String s) method for valid and invalid inputs    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidParseShort();
        testInvalidParseShort();
    }

    private static void testValidParseShort() {
        String[] validInputs = {"0", "1", "-1", "32767", "-32768"};
        short[] expectedOutputs = {0, 1, -1, 32767, -32768};
        for (int i = 0; i < validInputs.length; i++) {
            short result = Short.parseShort(validInputs[i]);
            if (result != expectedOutputs[i]) {
                throw new AssertionError("Failed for input: " + validInputs[i] + ", expected: " + expectedOutputs[i] + ", but got: " + result);
            }
        }
    }

    private static void testInvalidParseShort() {
        String[] invalidInputs = {"", "abc", "32768", "-32769", "12a"};
        for (String input : invalidInputs) {
            try {
                Short.parseShort(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // Expected exception, test passes for this input
            }
        }
    }
}
