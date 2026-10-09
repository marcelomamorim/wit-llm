/* @test
   @summary Tests the decode method of java.lang.Short.
   @run main ShortWitupTest_Pc4ba3532b728
 */
import java.lang.Short;
public class ShortWitupTest_Pc4ba3532b728 {
    public static void main(String[] args) {
        testDecodeValid();
        testDecodeNegative();
        testDecodeOutOfRange();
        testDecodeInvalidFormat();
    }

    static void testDecodeValid() {
        String input = "12345";
        Short result = Short.decode(input);
        assert result != null : "Result should not be null";
        assert result.shortValue() == 12345 : "Expected 12345, got " + result;
    }

    static void testDecodeNegative() {
        String input = "-12345";
        Short result = Short.decode(input);
        assert result != null : "Result should not be null";
        assert result.shortValue() == -12345 : "Expected -12345, got " + result;
    }

    static void testDecodeOutOfRange() {
        String input = "40000"; // out of Short range
        try {
            Short.decode(input);
            throw new AssertionError("Expected NumberFormatException for out of range");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    static void testDecodeInvalidFormat() {
        String input = "abc";
        try {
            Short.decode(input);
            throw new AssertionError("Expected NumberFormatException for invalid format");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
