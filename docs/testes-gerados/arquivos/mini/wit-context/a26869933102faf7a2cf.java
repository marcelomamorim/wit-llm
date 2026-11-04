/*
 * @test
 * @summary Test java.lang.Byte.decode(String) for valid and out-of-range inputs
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testDecodeValid();
        testDecodeOutOfRange();
    }

    private static void testDecodeValid() {
        // Valid inputs within byte range
        String[] validInputs = {"0", "127", "-128", "+10", "-0", "0x7F", "0X7f", "0177", "-0x80"};
        for (String input : validInputs) {
            try {
                Byte b = Byte.decode(input);
                int val = b.byteValue();
                if (val < Byte.MIN_VALUE || val > Byte.MAX_VALUE) {
                    throw new RuntimeException("Decoded value out of byte range for input: " + input);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Unexpected NumberFormatException for valid input: " + input, e);
            }
        }
    }

    private static void testDecodeOutOfRange() {
        // Inputs that decode to int values outside byte range should throw NumberFormatException
        String[] outOfRangeInputs = {"128", "-129", "0x80", "-0x81", "0200", "-0211"};
        for (String input : outOfRangeInputs) {
            boolean thrown = false;
            try {
                Byte.decode(input);
            } catch (NumberFormatException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected NumberFormatException not thrown for input: " + input);
            }
        }
    }
}
