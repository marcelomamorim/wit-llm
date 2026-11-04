/*
 * @test
 * @summary Test java.util.Base64 encode(byte[] src, byte[] dst) method
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeReturnsCorrectLength();
        testEncodeThrowsOnSmallDst();
    }

    private static void testEncodeReturnsCorrectLength() {
        byte[] src = { (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04 };
        Base64.Encoder encoder = Base64.getEncoder();
        int expectedLen = getExpectedOutputLength(src.length);
        byte[] dst = new byte[expectedLen];
        int ret = encoder.encode(src, dst);
        if (ret != expectedLen) {
            throw new RuntimeException("encode returned length " + ret + ", expected " + expectedLen);
        }
    }

    private static void testEncodeThrowsOnSmallDst() {
        byte[] src = { (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04 };
        Base64.Encoder encoder = Base64.getEncoder();
        int expectedLen = getExpectedOutputLength(src.length);
        byte[] dst = new byte[expectedLen - 1]; // deliberately too small
        try {
            encoder.encode(src, dst);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static int getExpectedOutputLength(int srcLen) {
        // Base64 output length calculation: 4 * ((srcLen + 2) / 3)
        return 4 * ((srcLen + 2) / 3);
    }
}
