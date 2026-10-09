/* @test
   @summary Verifies the behavior of Integer.decode with various string inputs.
   @run main IntegerWitupTest_P3badf3953f48
 */
import java.lang.Integer;
public class IntegerWitupTest_P3badf3953f48 {
    public static void main(String[] args) {
        testDecodeDecimal();
        testDecodeHexLowercase();
        testDecodeHexUppercase();
        testDecodeOctal();
        testDecodeWithSign();
        testDecodeInvalid();
        testDecodeEmpty();
        testDecodeSignInWrongPosition();
    }

    static void testDecodeDecimal() {
        Integer result = Integer.decode("12345");
        assert result.equals(Integer.valueOf(12345)) : "Failed decimal decode";
    }

    static void testDecodeHexLowercase() {
        Integer result = Integer.decode("0x1a");
        assert result.equals(Integer.valueOf(26)) : "Failed lowercase hex decode";
    }

    static void testDecodeHexUppercase() {
        Integer result = Integer.decode("0X1A");
        assert result.equals(Integer.valueOf(26)) : "Failed uppercase hex decode";
    }

    static void testDecodeOctal() {
        Integer result = Integer.decode("077");
        assert result.equals(Integer.valueOf(63)) : "Failed octal decode";
    }

    static void testDecodeWithSign() {
        Integer resultPos = Integer.decode("+123");
        Integer resultNeg = Integer.decode("-123");
        assert resultPos.equals(Integer.valueOf(123)) : "Failed plus sign decode";
        assert resultNeg.equals(Integer.valueOf(-123)) : "Failed minus sign decode";
    }

    static void testDecodeInvalid() {
        try {
            Integer.decode("0xGHI");
            assert false : "Expected NumberFormatException for invalid hex";
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            Integer.decode("08");
            assert false : "Expected NumberFormatException for invalid octal";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeEmpty() {
        try {
            Integer.decode("");
            assert false : "Expected NumberFormatException for empty string";
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeSignInWrongPosition() {
        try {
            Integer.decode("-+123");
            assert false : "Expected NumberFormatException for sign in wrong position";
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
