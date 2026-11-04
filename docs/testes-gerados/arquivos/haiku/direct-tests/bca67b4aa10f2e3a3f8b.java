/*
 * @test
 * @summary Basic behavior of the GCMParameterSpec(int, byte[]) constructor
 * @run main GCMParameterSpecWitupTest
 */

import javax.crypto.spec.GCMParameterSpec;

public class GCMParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullSrcThrowsIllegalArgumentException();
        testValidArgumentsKeepTagLengthAndIv();
        testIvBufferIsCopied();
        testNegativeTagLengthThrowsIllegalArgumentException();
    }

    static void testNullSrcThrowsIllegalArgumentException() {
        try {
            new GCMParameterSpec(128, null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null src");
    }

    static void testValidArgumentsKeepTagLengthAndIv() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        if (spec.getTLen() != 128) {
            throw new RuntimeException("Unexpected tLen: " + spec.getTLen());
        }
        byte[] result = spec.getIV();
        if (result.length != iv.length) {
            throw new RuntimeException("Unexpected IV length: " + result.length);
        }
        for (int i = 0; i < iv.length; i++) {
            if (result[i] != iv[i]) {
                throw new RuntimeException("IV mismatch at index " + i);
            }
        }
    }

    static void testIvBufferIsCopied() {
        byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        GCMParameterSpec spec = new GCMParameterSpec(96, iv);
        iv[0] = (byte) 0x7F;
        if (spec.getIV()[0] != 1) {
            throw new RuntimeException("IV was not copied defensively");
        }
    }

    static void testNegativeTagLengthThrowsIllegalArgumentException() {
        try {
            new GCMParameterSpec(-1, new byte[12]);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for negative tLen");
    }
}
