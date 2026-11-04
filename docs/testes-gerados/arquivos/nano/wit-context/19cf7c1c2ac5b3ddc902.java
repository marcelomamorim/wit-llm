/* @test    @summary Test Integer.parseInt(String s) for exception paths    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testParseIntNull();
        testParseIntInvalidFormat();
        testParseIntValid();
    }

    private static void testParseIntNull() {
        try {
            Integer.parseInt(null);
            System.err.println("Failed: Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type for null input: " + e);
        }
    }

    private static void testParseIntInvalidFormat() {
        String[] invalidInputs = {"abc", "12.34", ""};
        for (String input : invalidInputs) {
            try {
                Integer.parseInt(input);
                System.err.println("Failed: Expected NumberFormatException for input: '" + input + "'");
            } catch (NumberFormatException e) {
                // Expected exception
            } catch (Exception e) {
                System.err.println("Failed: Unexpected exception type for input: '" + input + "': " + e);
            }
        }
    }

    private static void testParseIntValid() {
        String[] validInputs = {"0", "123", "-456", "2147483647", "-2147483648"};
        int[] expectedValues = {0, 123, -456, Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int i = 0; i < validInputs.length; i++) {
            try {
                int result = Integer.parseInt(validInputs[i]);
                if (result != expectedValues[i]) {
                    System.err.println("Failed: parseInt('" + validInputs[i] + "') returned " + result + " instead of " + expectedValues[i]);
                }
            } catch (Exception e) {
                System.err.println("Failed: Unexpected exception for input: '" + validInputs[i] + "': " + e);
            }
        }
    }
}
