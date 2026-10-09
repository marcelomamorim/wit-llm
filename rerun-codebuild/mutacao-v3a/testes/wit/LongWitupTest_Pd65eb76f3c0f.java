/* @test    @summary Test for Long.decode(String) method to verify exception on empty string and normal decoding.
 * @run main LongWitupTest_Pd65eb76f3c0f
 */

import java.lang.Long;

public class LongWitupTest_Pd65eb76f3c0f {

    public static void main(String[] args) {
        testDecodeEmptyString();
        testDecodeValidHex();
        testDecodeValidDecimal();
        testDecodeInvalidSignPosition();
    }

    static void testDecodeEmptyString() {
        try {
            Long.decode("");
            System.err.println("Failed: Expected NumberFormatException for empty string");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    static void testDecodeValidHex() {
        Long result = Long.decode("0x10");
        if (result == null || result.longValue() != 16L) {
            System.err.println("Failed: decode("0x10") should return 16L");
            System.exit(1);
        }
    }

    static void testDecodeValidDecimal() {
        Long result = Long.decode("12345");
        if (result == null || result.longValue() != 12345L) {
            System.err.println("Failed: decode(\"12345\") should return 12345L");
            System.exit(1);
        }
    }

    static void testDecodeInvalidSignPosition() {
        try {
            Long.decode("-+123");
            System.err.println("Failed: Expected NumberFormatException for sign in wrong position");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
