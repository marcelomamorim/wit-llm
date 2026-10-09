/* @test
   @summary Basic tests for Integer.decode method.
   @run main IntegerWitupTest_P3badf3953f48
 */
import java.lang.Integer;
public class IntegerWitupTest_P3badf3953f48 {
    public static void main(String[] args) {
        testDecodeEmptyString();
        testDecodeNegativeSignInWrongPosition();
        testDecodeValidHex();
        testDecodeValidOctal();
        testDecodeValidDecimal();
        testDecodeInvalidFormat();
    }

    static void testDecodeEmptyString() {
        try {
            Integer.decode("");
            throw new AssertionError("Expected NumberFormatException for empty string");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeNegativeSignInWrongPosition() {
        String input = "-123";
        try {
            Integer.decode(input);
            throw new AssertionError("Expected NumberFormatException for sign in wrong position");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeValidHex() {
        String input = "0x1A";
        Integer result = Integer.decode(input);
        if (result == null || result.intValue() != 26) {
            throw new AssertionError("Failed to decode valid hex: " + input);
        }
    }

    static void testDecodeValidOctal() {
        String input = "012";
        Integer result = Integer.decode(input);
        if (result == null || result.intValue() != 10) {
            throw new AssertionError("Failed to decode valid octal: " + input);
        }
    }

    static void testDecodeValidDecimal() {
        String input = "123";
        Integer result = Integer.decode(input);
        if (result == null || result.intValue() != 123) {
            throw new AssertionError("Failed to decode valid decimal: " + input);
        }
    }

    static void testDecodeInvalidFormat() {
        String input = "+-123";
        try {
            Integer.decode(input);
            throw new AssertionError("Expected NumberFormatException for invalid format: " + input);
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
