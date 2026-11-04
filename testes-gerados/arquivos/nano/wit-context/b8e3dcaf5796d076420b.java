/* @test    @summary Test IvParameterSpec constructor with byte array, offset, and length for exception paths    @run main IvParameterSpecWitupTest */

import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullIv();
        testShortBuffer();
        testNegativeLen();
    }

    private static void testNullIv() {
        try {
            new IvParameterSpec(null, 0, 1);
            System.err.println("Failed: Expected IllegalArgumentException for null iv");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testShortBuffer() {
        byte[] iv = {1, 2, 3};
        int offset = 0;
        int len = 4; // intentionally too large
        try {
            new IvParameterSpec(iv, offset, len);
            System.err.println("Failed: Expected IllegalArgumentException for buffer too short");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testNegativeLen() {
        byte[] iv = {1, 2, 3};
        int offset = 0;
        int len = -1; // negative length
        try {
            new IvParameterSpec(iv, offset, len);
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException for negative len");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
