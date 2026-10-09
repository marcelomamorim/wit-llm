/*
 * @test
 * @summary Test java.lang.Byte.decode(String) method for valid and invalid inputs
 * @run main ByteWitupTest_P945c389a6ac7
 */
public class ByteWitupTest_P945c389a6ac7 {
    public static void main(String[] args) {
        testValidDecode();
        testDecodeOutOfRangeLow();
        testDecodeOutOfRangeHigh();
        testDecodeInvalidFormat();
    }

    private static void testValidDecode() {
        // Test decimal
        java.lang.Byte b1 = java.lang.Byte.decode("127");
        if (b1.byteValue() != 127) throw new RuntimeException("Failed decode decimal max");

        java.lang.Byte b2 = java.lang.Byte.decode("-128");
        if (b2.byteValue() != -128) throw new RuntimeException("Failed decode decimal min");

        java.lang.Byte b3 = java.lang.Byte.decode("0");
        if (b3.byteValue() != 0) throw new RuntimeException("Failed decode decimal zero");

        // Test hex with 0x prefix
        java.lang.Byte b4 = java.lang.Byte.decode("0x7f");
        if (b4.byteValue() != 127) throw new RuntimeException("Failed decode hex max");

        // Test hex with # prefix
        java.lang.Byte b5 = java.lang.Byte.decode("#7f");
        if (b5.byteValue() != 127) throw new RuntimeException("Failed decode hex # prefix max");

        // Test octal with leading zero
        java.lang.Byte b6 = java.lang.Byte.decode("0177");
        if (b6.byteValue() != 127) throw new RuntimeException("Failed decode octal max");
    }

    private static void testDecodeOutOfRangeLow() {
        try {
            java.lang.Byte.decode("-129");
            throw new RuntimeException("Expected NumberFormatException for value below MIN_VALUE");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testDecodeOutOfRangeHigh() {
        try {
            java.lang.Byte.decode("128");
            throw new RuntimeException("Expected NumberFormatException for value above MAX_VALUE");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testDecodeInvalidFormat() {
        try {
            java.lang.Byte.decode("notANumber");
            throw new RuntimeException("Expected NumberFormatException for invalid format");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
